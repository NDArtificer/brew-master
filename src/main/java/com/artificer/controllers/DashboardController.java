package com.artificer.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artificer.repository.CervejasRepository;
import com.artificer.repository.ClienteRepository;
import com.artificer.repository.PedidoRepository;

@Controller
public class DashboardController {

	@NumberFormat(pattern = "##0.00")
	BigDecimal vendaAno;

	@NumberFormat(pattern = "##0.00")
	BigDecimal vendaMes;

	@NumberFormat(pattern = "##0.00")
	BigDecimal ticketMedio;
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CervejasRepository cervejasRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		this.vendaAno = pedidoRepository.valorTotalVendasAno();
		this.vendaMes = pedidoRepository.valorTotalVendasMes();
		this.ticketMedio = pedidoRepository.valorTicketMedio();
		mv.addObject("vendasAno", vendaAno);
		mv.addObject("vendasMes", vendaMes);
		mv.addObject("ticketMedio", ticketMedio.setScale(2, RoundingMode.HALF_UP));

		mv.addObject("valorItensEstoque", cervejasRepository.valorItensEstoque());
		mv.addObject("totalClientes", clienteRepository.count());

		return mv;
	}
}
