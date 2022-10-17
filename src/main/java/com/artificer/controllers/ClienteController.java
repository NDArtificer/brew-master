package com.artificer.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.model.Cliente;
import com.artificer.model.enums.TipoPessoa;
import com.artificer.repository.EstadoRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private EstadoRepository estadoRepository;

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
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estadoRepository.findAll());

		if (result.hasErrors()) {
			System.out.println("Tem Erros no Elemento!");
		} else {
			System.out.println("Cadastro de nova cerveja");
			System.out.println("Sku cerveja: " + cliente.getNome());
			System.out.println("Sku cerveja: " + cliente.getEmail());
			atributes.addFlashAttribute("message", "Cerveja salva com sucesso!");
			mv.setViewName("redirect:/clientes/cadastro");
		}

		return mv;
	}

}
