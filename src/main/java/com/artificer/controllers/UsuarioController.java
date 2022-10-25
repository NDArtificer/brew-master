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

import com.artificer.exceptions.EmailJaCadastradoException;
import com.artificer.model.Usuario;
import com.artificer.services.CadastroUsuarioService;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private CadastroUsuarioService usuarioService;

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
			try {
				usuarioService.save(usuario);
			} catch (EmailJaCadastradoException e) {
				result.rejectValue("email", e.getMessage(), e.getMessage());
				return home(usuario);
			}
			atributes.addFlashAttribute("message", "Usuário salvo com sucesso!");
			return new ModelAndView("redirect:/usuarios/cadastro");
		}

	}

}
