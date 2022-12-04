package com.artificer.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.ItensPedidoSession;
import com.artificer.controllers.pages.PageWrapper;
import com.artificer.controllers.validator.PedidoValidator;
import com.artificer.model.Cerveja;
import com.artificer.model.Pedido;
import com.artificer.model.enums.StatusVenda;
import com.artificer.repository.PedidoRepository;
import com.artificer.repository.filter.PedidoFilter;
import com.artificer.security.UsuarioSistema;
import com.artificer.services.CadastroCervejaService;
import com.artificer.services.CadastroPedidoService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private CadastroCervejaService cervejaService;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CadastroPedidoService pedidoService;

	@Autowired
	private ItensPedidoSession itens;

	@Autowired
	private PedidoValidator pedidoValidator;

	public void initializeValidator(WebDataBinder binder) {
		binder.setValidator(pedidoValidator);
	}

	@GetMapping("/novo")
	public ModelAndView peididos(Pedido pedido) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pedidos/cadastroPedido");

		setUuid(pedido);

		mv.addObject("itens", pedido.getItens());
		mv.addObject("valorFrete", pedido.getValorFrete());
		mv.addObject("valorDesconto", pedido.getValorDesconto());
		mv.addObject("valorTotalItens", itens.getValorTotal(pedido.getUuid()));
		return mv;
	}

	@GetMapping
	public ModelAndView pesquisar(PedidoFilter pedidoFilter, BindingResult result,
			@PageableDefault(size = 4) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("pedidos/PesquisaPedidos");
		mv.addObject("statuses", StatusVenda.values());

		PageWrapper<Pedido> pages = new PageWrapper<>(pedidoRepository.filtrar(pedidoFilter, pageable),
				httpServletRequest);
		mv.addObject("paginas", pages);
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		Pedido pedido = pedidoRepository.findWithItens(id);
		setUuid(pedido);
		pedido.getItens().forEach(i -> {
			itens.adicionarItem(pedido.getUuid(), i.getCerveja(), i.getQuantidade());
		});

		ModelAndView mv = peididos(pedido);
		mv.addObject(pedido);
		return mv;
	}

	@PostMapping(value = "/novo", params = "salvar")
	public ModelAndView salvarPedido(Pedido pedido, BindingResult result, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validatePedido(pedido, result);
		if (result.hasErrors()) {
			return peididos(pedido);
		}
		pedido.setUsuario(usuarioSistema.getUsuario());
		pedidoService.salvar(pedido);
		redirectAttributes.addFlashAttribute("message", "Pedido Realizado com sucesso!");
		return new ModelAndView("redirect:/pedidos/novo");
	}

	@PostMapping(value = "/novo", params = "emitir")
	public ModelAndView emitirPedido(Pedido pedido, BindingResult result, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validatePedido(pedido, result);
		if (result.hasErrors()) {
			return peididos(pedido);
		}
		pedido.setUsuario(usuarioSistema.getUsuario());
		pedidoService.emitir(pedido);
		redirectAttributes.addFlashAttribute("message", "Pedido realizado e emitido com sucesso!");
		return new ModelAndView("redirect:/pedidos/novo");
	}

	@PostMapping(value = "/novo", params = "cancelar")
	public ModelAndView cancelarPedido(Pedido pedido, BindingResult result, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		try {
			pedidoService.cancelar(pedido);

		} catch (AccessDeniedException e) {
			ModelAndView mv = new ModelAndView("error");
			mv.addObject("status", 403);
			return mv;
		}
		redirectAttributes.addFlashAttribute("message", "Pedido cancelado com sucesso!");
		return new ModelAndView(String.format("redirect:/pedidos/%s", pedido.getId()));
	}

	@PostMapping(value = "/novo", params = "email")
	public ModelAndView emailPedido(Pedido pedido, BindingResult result, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validatePedido(pedido, result);
		if (result.hasErrors()) {
			return peididos(pedido);
		}
		pedido.setUsuario(usuarioSistema.getUsuario());
		pedidoService.enviarEmail(pedido);
		redirectAttributes.addFlashAttribute("message", "Pedido salvo e e-mail enviado!");
		return new ModelAndView("redirect:/pedidos/novo");
	}

	@PostMapping("/item")
	public ModelAndView adicionarItem(Long id, String uuid) {
		Cerveja cerveja = cervejaService.buscar(id);
		itens.adicionarItem(uuid, cerveja, 1);
		return mvItensPedido(uuid);
	}

	@PutMapping("/item/{id}")
	public ModelAndView adicionarItem(@PathVariable Long id, Integer quantidade, String uuid) {
		Cerveja cerveja = cervejaService.buscar(id);
		itens.alterarQuantidadeItens(uuid, cerveja, quantidade);
		return mvItensPedido(uuid);
	}

	@DeleteMapping("/item/{uuid}/{id}")
	public ModelAndView removerItem(@PathVariable Long id, Integer quantidade, @PathVariable String uuid) {
		Cerveja cerveja = cervejaService.buscar(id);
		itens.removerItem(uuid, cerveja);
		return mvItensPedido(uuid);
	}

	private ModelAndView mvItensPedido(String uuid) {
		ModelAndView mv = new ModelAndView("pedidos/ItensPedido");
		mv.addObject("itens", itens.getItens(uuid));
		mv.addObject("valorTotal", itens.getValorTotal(uuid));
		return mv;
	}

	private void validatePedido(Pedido pedido, BindingResult result) {
		pedido.addItens(itens.getItens(pedido.getUuid()));
		pedido.calcularValorTotal();

		pedidoValidator.validate(pedido, result);
	}

	private void setUuid(Pedido pedido) {
		if (!StringUtils.hasText(pedido.getUuid())) {
			pedido.setUuid(UUID.randomUUID().toString());
		}
	}

}
