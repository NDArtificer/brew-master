package com.artificer.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.controllers.pages.PageWrapper;
import com.artificer.exceptions.EntidadeEmUsoException;
import com.artificer.exceptions.NomeEstiloJaCadastradoException;
import com.artificer.model.Estilo;
import com.artificer.model.Usuario;
import com.artificer.repository.EstiloRepository;
import com.artificer.services.CadastroEstiloService;

@Controller
@RequestMapping("/estilos")
public class EstiloController {

	@Autowired
	private CadastroEstiloService cadastroEstiloService;

	@Autowired
	private EstiloRepository estiloRepository;

	@GetMapping("/cadastro")
	public ModelAndView cadastro(Estilo estilo) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("estilo/CadastroEstilo");
		return mv;

	}

	@GetMapping
	public ModelAndView pesquisar(@RequestParam(value = "nomeEstilo", required = false) String nomeEstilo,
			@PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("estilo/PesquisaEstilo");
		// mv.addObject("nomeEstilo", nomeEstilo);
		PageWrapper<Estilo> pages = new PageWrapper<>(estiloRepository.filtrar(nomeEstilo, pageable),
				httpServletRequest);
		mv.addObject("paginas", pages);
		return mv;

	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		Estilo estilo = estiloRepository.findById(id).get();
		ModelAndView mv = cadastro(estilo);
		mv.addObject(estilo);
		return mv;
	}

	@PostMapping(value = { "/cadastro", "{\\d+}" })
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes atributes) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("estilo/CadastroEstilo");

		if (result.hasErrors()) {
			return cadastro(estilo);

		}
		System.out.println("Cadastro de novo estilo");
		try {

			cadastroEstiloService.save(estilo);
		} catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return cadastro(estilo);
		}
		atributes.addFlashAttribute("message", "Estilo salvo com sucesso!");
		return new ModelAndView("redirect:/estilos/cadastro");

	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	private @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}

		estilo = cadastroEstiloService.save(estilo);

		return ResponseEntity.ok(estilo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable("id") Estilo estilo) {
		try {
			cadastroEstiloService.excluir(estilo);
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
