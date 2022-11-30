package com.artificer.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.ItensPedidoSession;
import com.artificer.controllers.validator.PedidoValidator;
import com.artificer.model.Cerveja;
import com.artificer.model.Pedido;
import com.artificer.security.UsuarioSistema;
import com.artificer.services.CadastroCervejaService;
import com.artificer.services.CadastroPedidoService;
import com.artificer.venda.ItensPedidos;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private CadastroCervejaService cervejaService;

	@Autowired
	private CadastroPedidoService pedidoService;

	@Autowired
	private ItensPedidoSession itens;

	@Autowired
	private PedidoValidator pedidoValidator;

	@InitBinder
	public void initializeValidator(WebDataBinder binder) {
		binder.setValidator(pedidoValidator);
	}

	@GetMapping("/novo")
	public ModelAndView peididos(Pedido pedido) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pedidos/cadastroPedido");

		if (!StringUtils.hasText(pedido.getUuid())) {
			pedido.setUuid(UUID.randomUUID().toString());
		}

		mv.addObject("itens", pedido.getItens());
		mv.addObject("valorFrete", pedido.getValorFrete());
		mv.addObject("valorDesconto", pedido.getValorDesconto());
		mv.addObject("valorTotalItens", itens.getValorTotal(pedido.getUuid()));
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvarPedido(Pedido pedido, BindingResult result, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		pedido.addItens(itens.getItens(pedido.getUuid()));
		pedido.calcularValorTotal();

		pedidoValidator.validate(pedido, result);
		if (result.hasErrors()) {
			return peididos(pedido);
		}
		pedido.setUsuario(usuarioSistema.getUsuario());
		pedidoService.salvar(pedido);
		redirectAttributes.addFlashAttribute("message", "Pedido Realizado com sucesso!");
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
}
