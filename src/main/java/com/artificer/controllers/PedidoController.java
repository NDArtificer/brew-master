package com.artificer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artificer.model.Cerveja;
import com.artificer.services.CadastroCervejaService;
import com.artificer.venda.ItensPedidos;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private CadastroCervejaService cervejaService;

	@Autowired
	private ItensPedidos itens;

	@GetMapping("/novo")
	public ModelAndView peididos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pedidos/cadastroPedido");

		return mv;
	}

	@PostMapping("/item")
	public ModelAndView adicionarItem(Long id) {
		Cerveja cerveja = cervejaService.buscar(id);
		itens.adicionarItem(cerveja, 1);
		ModelAndView mv = new ModelAndView("pedidos/ItensPedido");
		mv.addObject("itens", itens.getItens());
		return mv;
	}

	@PutMapping("/item/{id}")
	public ModelAndView adicionarItem(@PathVariable Long id, Integer quantidade) {
		Cerveja cerveja = cervejaService.buscar(id);
		itens.alterarQuantidadeItens(cerveja, quantidade);
		ModelAndView mv = new ModelAndView("pedidos/ItensPedido");
		mv.addObject("itens", itens.getItens());
		return mv;
	}
}
