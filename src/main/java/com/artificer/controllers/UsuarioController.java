package com.artificer.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.model.Usuario;

@Controller
public class UsuarioController {

	@GetMapping("/usuarios/cadastro")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuarios/CadastroUsuarios");
		mv.addObject("usuario", new Usuario());
		return mv;
	}

	@PostMapping("/usuarios/cadastro")
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, RedirectAttributes atributes) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuarios/CadastroUsuarios");

		if (result.hasErrors()) {
			System.out.println("Tem Erros no Elemento!");
		} else {
			System.out.println("Cadastro de nova cerveja");
			atributes.addFlashAttribute("message", "Cerveja salva com sucesso!");
			mv.setViewName("redirect:/usuarios");
		}

		return mv;
	}

}
