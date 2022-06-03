package com.artificer.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.model.Cidade;

@Controller
public class CidadeController {

	@GetMapping("/cidades/cadastro")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cidade/CadastroCidade");
		mv.addObject("cidade", new Cidade());
		return mv;
	}

	@PostMapping("/cidades/cadastro")
	public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, RedirectAttributes atributes) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cidade/CadastroCidade");

		if (result.hasErrors()) {
			System.out.println("Tem Erros no Elemento!");
		} else {
			System.out.println("Cadastro de nova cerveja");
			atributes.addFlashAttribute("message", "Cerveja salva com sucesso!");
			mv.setViewName("redirect:/cidades");
		}

		return mv;
	}

}
