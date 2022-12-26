package com.artificer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artificer.input.PeriodoRelatorio;
import com.artificer.services.RelatorioService;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {

	@Autowired
	private RelatorioService service;

	@GetMapping("/pedidosEmitidos")
	public ModelAndView relatorioPedidosEmitidos() {
		ModelAndView mv = new ModelAndView("relatorios/RelatoriosPedidosEmitidos");
		mv.addObject(new PeriodoRelatorio());
		return mv;
	}

	@PostMapping("/pedidosEmitidos")
	public ResponseEntity<byte[]> gerarRelatorioPedidosEmitidos(PeriodoRelatorio periodoRelatorio) throws Exception {
		byte[] relatorio = service.gerarRelatorioVendaEmitidas(periodoRelatorio);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

}
