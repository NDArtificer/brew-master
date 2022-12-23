package com.artificer.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.controllers.pages.PageWrapper;
import com.artificer.exceptions.EmailJaCadastradoException;
import com.artificer.exceptions.EntidadeEmUsoException;
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

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findUserWithGroups(id);
		ModelAndView mv = home(usuario);
		mv.addObject(usuario);
		return mv;
	}

	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuariofilter, BindingResult result,
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuarios/PesquisaUsuario");
		mv.addObject("grupos", grupoRepository.findAll());

		PageWrapper<Usuario> pages = new PageWrapper<>(usuarioRepository.filtrar(usuariofilter, pageable),
				httpServletRequest);
		mv.addObject("paginas", pages);

		return mv;

	}

	@PostMapping(value = { "/cadastro", "{\\d+}" })
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
	public void atualizar(@RequestParam("codigos[]") Long[] codigos,
			@RequestParam("status") StatusUsuario statusUsuario) {
		usuarioService.alterarStatus(codigos, statusUsuario);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable("id") Usuario usuario) {
		try {
			usuarioService.excluir(usuario);
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
