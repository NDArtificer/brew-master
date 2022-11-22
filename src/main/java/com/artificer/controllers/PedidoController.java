package com.artificer.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artificer.ItensPedidoSession;
import com.artificer.model.Cerveja;
import com.artificer.services.CadastroCervejaService;
import com.artificer.venda.ItensPedidos;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private CadastroCervejaService cervejaService;

	@Autowired
	private ItensPedidoSession itens;

	@GetMapping("/novo")
	public ModelAndView peididos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pedidos/cadastroPedido");
		mv.addObject("uuid", UUID.randomUUID().toString());
		return mv;
	}

	@PostMapping("/item")
	public ModelAndView adicionarItem(Long id, String uuid) {
		Cerveja cerveja = cervejaService.buscar(id);
		itens.adicionarItem(uuid, cerveja, 1);
		return mvItensPedido(uuid);
	}

	@PutMapping("/item/{id}")
	public ModelAndView adicionarItem(@PathVariable Long id, Integer quantidade, String uuid) {
		Cerveja cerveja = cervejaService.buscar(id);
		itens.alterarQuantidadeItens(uuid, cerveja, quantidade);
		return mvItensPedido(uuid);
	}

	@DeleteMapping("/item/{uuid}/{id}")
	public ModelAndView removerItem(@PathVariable Long id, Integer quantidade, @PathVariable String uuid) {
		Cerveja cerveja = cervejaService.buscar(id);
		itens.removerItem(uuid, cerveja);
		return mvItensPedido(uuid);
	}

	private ModelAndView mvItensPedido(String uuid) {
		ModelAndView mv = new ModelAndView("pedidos/ItensPedido");
		mv.addObject("itens", itens.getItens(uuid));
		mv.addObject("valorTotal", itens.getValorTotal(uuid));
		return mv;
	}
}