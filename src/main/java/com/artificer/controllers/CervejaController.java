package com.artificer.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.controllers.pages.PageWrapper;
import com.artificer.exceptions.CervejaEmUsoException;
import com.artificer.model.Cerveja;
import com.artificer.model.enums.Origem;
import com.artificer.model.enums.Sabor;
import com.artificer.output.CervejaSummary;
import com.artificer.repository.CervejasRepository;
import com.artificer.repository.EstiloRepository;
import com.artificer.repository.filter.CervejaFilter;
import com.artificer.services.CadastroCervejaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cervejas")
public class CervejaController {

	@Autowired
	private CadastroCervejaService cervejaService;

	@Autowired
	private CervejasRepository cervejaRepository;

	@Autowired
	private EstiloRepository estiloRepository;

	@GetMapping("/cadastro")
	public ModelAndView cadastro(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		mv.addObject("estilos", estiloRepository.findAll());

		return mv;
	}

	@PostMapping(value = { "/cadastro", "{\\d+}" })
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes atributes) {

		if (result.hasErrors()) {
			log.info("Tem Erros no Elemento!");
			return cadastro(cerveja);
		} else {

			cervejaService.save(cerveja);
			atributes.addFlashAttribute("message", "Cerveja salva com sucesso!");
			return new ModelAndView("redirect:/cervejas/cadastro");
		}
	}

	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result,
			@PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		mv.addObject("estilos", estiloRepository.findAll());

		PageWrapper<Cerveja> pages = new PageWrapper<>(cervejaRepository.filtrar(cervejaFilter, pageable),
				httpServletRequest);
		mv.addObject("paginas", pages);

		return mv;
	}

	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CervejaSummary> pesquisar(String skuOuNome) {
		return cervejaRepository.porSkuOuNome(skuOuNome);
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Cerveja cerveja) {
		ModelAndView mv = cadastro(cerveja);
		mv.addObject(cerveja);
		return mv;
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Cerveja cerveja) {
		try {
			cervejaService.excluir(cerveja);

		} catch (CervejaEmUsoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.noContent().build();

	}

}
