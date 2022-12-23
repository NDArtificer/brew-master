package com.artificer.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.controllers.pages.PageWrapper;
import com.artificer.exceptions.CidadeJaCadastradaExeption;
import com.artificer.exceptions.EntidadeEmUsoException;
import com.artificer.model.Cidade;
import com.artificer.model.Estilo;
import com.artificer.repository.CidadeRepository;
import com.artificer.repository.EstadoRepository;
import com.artificer.repository.filter.CidadeFilter;
import com.artificer.services.CadastroCidadeService;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cidadeService;

	@GetMapping("/cadastro")
	public ModelAndView home(Cidade cidade) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cidade/CadastroCidade");
		mv.addObject("estados", estadoRepository.findAll());
		return mv;
	}

	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadefilter, BindingResult result,
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidade");
		mv.addObject("estados", estadoRepository.findAll());

		PageWrapper<Cidade> pages = new PageWrapper<>(cidadeRepository.filtrar(cidadefilter, pageable),
				httpServletRequest);

		mv.addObject("paginas", pages);

		return mv;

	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Cidade cidade) {
		ModelAndView mv = home(cidade);
		mv.addObject(cidade);
		return mv;
	}

	@PostMapping(value = { "/cadastro", "{\\d+}" })
	@CacheEvict(value = "cidades", key = "#cidade.estado.id", condition = "#cidade.estadoIsNotNull()")
	public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, RedirectAttributes atributes) {

		if (result.hasErrors()) {
			System.out.println("Tem Erros no Elemento!");
			return home(cidade);
		} else {

			try {
				cidadeService.save(cidade);
			} catch (CidadeJaCadastradaExeption e) {
				result.rejectValue("nome", e.getMessage(), e.getMessage());
				return home(cidade);
			}
			atributes.addFlashAttribute("message", "Cidade salva com sucesso!");
			return new ModelAndView("redirect:/cidades/cadastro");
		}

	}

	@Cacheable(value = "cidades", key = "#estadoId")
	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cidade> buscarPeloIdEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long estadoId) {

		return cidadeRepository.findByEstadoId(estadoId);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable("id") Cidade cidade) {
		try {
			cidadeService.excluir(cidade);
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
