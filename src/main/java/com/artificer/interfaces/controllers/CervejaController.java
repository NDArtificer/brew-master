package com.artificer.interfaces.controllers;

import java.util.List;

import com.artificer.application.services.FotoStorageService;
import com.artificer.infrastructure.mapper.CervejaMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

import com.artificer.interfaces.controllers.pages.PageWrapper;
import com.artificer.domain.exceptions.CervejaEmUsoException;
import com.artificer.domain.model.Cerveja;
import com.artificer.domain.model.enums.Origem;
import com.artificer.domain.model.enums.Sabor;
import com.artificer.domain.output.CervejaSummary;
import com.artificer.infrastructure.repository.CervejasRepository;
import com.artificer.infrastructure.repository.EstiloRepository;
import com.artificer.infrastructure.repository.filter.CervejaFilter;
import com.artificer.application.services.CadastroCervejaService;

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

	@Autowired
	private FotoStorageService storageService;

	@Autowired
	private CervejaMapper cervejaMapper;

	@GetMapping("/cadastro")
	public ModelAndView cadastro(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		mv.addObject("estilos", estiloRepository.findAll());

		return mv;
	}

	@PostMapping(value = { "/cadastro", "/{id:\\d+}" })
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
		List<Cerveja> cervejas = cervejaRepository.findBySkuStartingWithIgnoreCaseOrNomeStartingWithIgnoreCase(skuOuNome, skuOuNome);
		List<CervejaSummary> modelList = cervejaMapper.toModel(cervejas);
		modelList.forEach(c -> {
			String thumbnailFoto = String.format("%s%s", FotoStorageService.THUMBNAIL_PREFIX, c.getFoto());
			c.setUrlThumbnailFoto(storageService.getUrl(thumbnailFoto));

		});
		return modelList;
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
