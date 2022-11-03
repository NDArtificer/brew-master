package com.artificer.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.exceptions.EmailJaCadastradoException;
import com.artificer.exceptions.SenhaNaoInformadaException;
import com.artificer.model.Usuario;
import com.artificer.model.enums.StatusUsuario;
import com.artificer.repository.GrupoRepository;
import com.artificer.repository.UsuarioRepository;
import com.artificer.repository.filter.UsuarioFilter;
import com.artificer.services.CadastroUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService usuarioService;

	@Autowired
	private GrupoRepository grupoRepository;

	@GetMapping("/cadastro")
	public ModelAndView home(Usuario usuario) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuarios/CadastroUsuarios");
		mv.addObject("grupos", grupoRepository.findAll());
		return mv;
	}

	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuariofilter, BindingResult result,
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuarios/PesquisaUsuario");
		mv.addObject("grupos", grupoRepository.findAll());
		mv.addObject("usuarios", usuarioRepository.filtrar(usuariofilter));

//		PageWrapper<Usuario> pages = new PageWrapper<>(usuarioRepository.findAll(pageable), httpServletRequest);
//		mv.addObject("paginas", pages);

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
			} catch (SenhaNaoInformadaException e) {
				result.rejectValue("senha", e.getMessage(), e.getMessage());
				return home(usuario);
			}
			atributes.addFlashAttribute("message", "Usuário salvo com sucesso!");
			return new ModelAndView("redirect:/usuarios/cadastro");
		}

	}

	@PutMapping("/status")
	@ResponseStatus(code = HttpStatus.OK)
	public void atualizar(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		usuarioService.alterarStatus(codigos, statusUsuario);
	}

}
