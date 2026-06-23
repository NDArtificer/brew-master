package com.artificer.application.services;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import javax.sql.DataSource;

import com.artificer.infrastructure.relatorio.RelatorioUtils;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artificer.domain.input.PeriodoRelatorio;

@Service
public class RelatorioService {

	@Autowired
	private DataSource dataSource;

	public byte[] gerarRelatorioVendaEmitidas(PeriodoRelatorio periodoRelatorio) {
		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.MIN)
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());

		InputStream logoStream = Objects.requireNonNull(getClass().getResourceAsStream("/static/layout/images/logo.png"));
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("data_inicio", dataInicio);
		parametros.put("data_fim", dataFim);
		parametros.put("logo", logoStream);
		parametros.put("tipoRelatorio", "Pedidos Emitidos");
		parametros.put(JRParameter.REPORT_LOCALE, Locale.of("pt", "BR"));
		parametros.put(JRParameter.REPORT_TIME_ZONE, TimeZone.getTimeZone("America/Sao_Paulo"));

		JasperReport jasperReport = RelatorioUtils.loadReport("relatorio_pedidos_emitidos");

		try (Connection con = dataSource.getConnection()) {
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException | SQLException e) {
			throw new RuntimeException("Erro ao gerar relatório de pedidos emitidos", e);
		}
	}

}
