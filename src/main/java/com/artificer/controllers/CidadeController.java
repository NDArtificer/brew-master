package com.artificer.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artificer.model.Cidade;
import com.artificer.repository.CidadeRepository;
import com.artificer.repository.EstadoRepository;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@GetMapping("/cadastro")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cidade/CadastroCidade");
		return mv;
	}

	@PostMapping("/cadastro")
	public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, RedirectAttributes atributes) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cidade/CadastroCidade");

		if (result.hasErrors()) {
			System.out.println("Tem Erros no Elemento!");
		} else {
			System.out.println("Cadastro de nova cerveja");
			atributes.addFlashAttribute("message", "Cidade salva com sucesso!");
			mv.setViewName("redirect:/cidades");
		}

		return mv;
	}

	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cidade> buscarPeloIdEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long estadoId) {

		return cidadeRepository.findByEstadoId(estadoId);

	}

}
