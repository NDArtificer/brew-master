package com.artificer.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.model.Usuario;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@GetMapping("/cadastro")
	public ModelAndView home(Usuario usuario) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuarios/CadastroUsuarios");
		return mv;
	}

	@PostMapping("/cadastro")
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, RedirectAttributes atributes) {

		if (result.hasErrors()) {
			System.out.println("Tem Erros no Elemento!");
			return home(usuario);
		} else {
			System.out.println("Cadastro de nova cerveja");
			atributes.addFlashAttribute("message", "Cerveja salva com sucesso!");
			return home(usuario);
		}

	}

}
