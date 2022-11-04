package com.artificer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@GetMapping("/novo")
	public ModelAndView peididos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pedidos/cadastroPedido");

		return mv;
	}
}
