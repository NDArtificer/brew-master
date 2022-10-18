package com.artificer.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.controllers.pages.PageWrapper;
import com.artificer.exceptions.CpfCnpjClienteCadastroException;
import com.artificer.model.Cliente;
import com.artificer.model.enums.TipoPessoa;
import com.artificer.repository.ClienteRepository;
import com.artificer.repository.EstadoRepository;
import com.artificer.repository.filter.ClienteFilter;
import com.artificer.services.CadastroClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroClienteService clienteService;

	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@GetMapping("/cadastro")
	public ModelAndView home(Cliente cliente) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estadoRepository.findAll());

		return mv;
	}

	@PostMapping("/cadastro")
	public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult result, RedirectAttributes atributes) {
		if (result.hasErrors()) {
			System.out.println("Tem Erros no Elemento!");
			return home(cliente);

		} else {

			try {

				clienteService.save(cliente);
			} catch (CpfCnpjClienteCadastroException e) {
				result.rejectValue("cpfCnpj", e.getMessage(), e.getMessage());
				return home(cliente);
			}
			atributes.addFlashAttribute("message", "Cliente salvo com sucesso!");
			return new ModelAndView("redirect:/clientes/cadastro");
		}

	}
	
	
	@GetMapping
	public  ModelAndView pesquisar(ClienteFilter clienteFilter,  BindingResult result,
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cliente/PesquisaCliente");
		PageWrapper<Cliente> pages = new PageWrapper<>(clienteRepository.filtrar(clienteFilter, pageable), httpServletRequest);
		
		mv.addObject("paginas", pages);
		
		return mv;
		
	}

}
