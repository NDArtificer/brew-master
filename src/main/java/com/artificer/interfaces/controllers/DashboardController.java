package com.artificer.interfaces.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.artificer.infrastructure.mapper.EstoqueMapper;
import com.artificer.infrastructure.projections.ValorItensEstoqueProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artificer.infrastructure.repository.CervejasRepository;
import com.artificer.infrastructure.repository.ClienteRepository;
import com.artificer.infrastructure.repository.PedidoRepository;

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

	@Autowired
	private EstoqueMapper estoqueMapper;

	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		this.vendaAno = pedidoRepository.valorTotalVendasAno();
		this.vendaMes = pedidoRepository.valorTotalVendasMes();
		this.ticketMedio = pedidoRepository.valorTicketMedio();
		mv.addObject("vendasAno", vendaAno);
		mv.addObject("vendasMes", vendaMes);
		mv.addObject("ticketMedio", ticketMedio.setScale(2, RoundingMode.HALF_UP));

		ValorItensEstoqueProjection estoqueProjection = cervejasRepository.valorItensEstoque();
		mv.addObject("valorItensEstoque", estoqueMapper.toDto(estoqueProjection));
		mv.addObject("totalClientes", clienteRepository.count());

		return mv;
	}
}
