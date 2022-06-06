package com.artificer.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.model.Cerveja;
import com.artificer.model.enums.Origem;
import com.artificer.model.enums.Sabor;
import com.artificer.repository.EstiloRepository;
import com.artificer.services.CadastroCervejaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CervejaController {

	@Autowired
	private CadastroCervejaService cervejaService;

	@Autowired
	private EstiloRepository estiloRepository;

	@GetMapping("/cerveja/cadastro")
	public ModelAndView cadastro(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		mv.addObject("estilos", estiloRepository.findAll());

		return mv;
	}

	@PostMapping("/cerveja/cadastro")
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes atributes) {

		if (result.hasErrors()) {
			log.info("Tem Erros no Elemento!");
			return cadastro(cerveja);
		} else {

			log.info("Cadastro de nova cerveja");
			log.info("Sku cerveja -> " + cerveja.getSku());
			log.info("Nome cerveja -> " + cerveja.getNome());
			log.info("Descicao cerveja -> " + cerveja.getDescricao());
			log.info("Estilo cerveja -> " + cerveja.getEstilo().getId());
			log.info("Estilo cerveja -> " + cerveja.getEstilo().getId());
			log.info("Comissao cerveja -> " + cerveja.getComissao());
			log.info("Valor cerveja -> " + cerveja.getValor());
			log.info("Sabor cerveja -> " + cerveja.getSabor());
			log.info("Teor Alcoolico cerveja -> " + cerveja.getTeorAlcoolico());
			log.info("Origem cerveja -> " + cerveja.getOrigem());
			log.info("Quantidade Estoque cerveja -> " + cerveja.getQuantidadeEstoque());

			cervejaService.save(cerveja);
			atributes.addFlashAttribute("message", "Cerveja salva com sucesso!");
			return new ModelAndView("redirect:/cerveja/cadastro");
		}
	}

}
