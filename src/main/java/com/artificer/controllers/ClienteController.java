package com.artificer.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.model.Cliente;

@Controller
public class ClienteController {

	@GetMapping("/clientes/cadastro")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cliente/CadastroCliente");
		mv.addObject("cliente", new Cliente());
		return mv;
	}

	@PostMapping("/clientes/cadastro")
	public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult result, RedirectAttributes atributes) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cliente/CadastroCliente");

		if (result.hasErrors()) {
			System.out.println("Tem Erros no Elemento!");
		} else {
			System.out.println("Cadastro de nova cerveja");
			System.out.println("Sku cerveja: " + cliente.getNome());
			System.out.println("Sku cerveja: " + cliente.getEmail());
			atributes.addFlashAttribute("message", "Cerveja salva com sucesso!");
			mv.setViewName("redirect:/clientes");
		}

		return mv;
	}

}
