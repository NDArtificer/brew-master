package com.artificer.services;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artificer.model.Pedido;
import com.artificer.repository.PedidoRepository;

@Service
public class CadastroPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional
	public void salvar(Pedido pedido) {
		if (pedido.isNovoPedido()) {
			pedido.setDataCriacao(LocalDateTime.now());
		}

		if (pedido.getDataEntrega() != null) {
			pedido.setDataHoraEntrega(LocalDateTime.of(pedido.getDataEntrega(), pedido.getHoraEntrega()));
		}
		pedidoRepository.save(pedido);
	}

	@Transactional
	public void enviarEmail(Pedido pedido) {
		// TODO Auto-generated method stub

	}

	@Transactional
	public void emitir(Pedido pedido) {
		pedido.emitir();
		salvar(pedido);

	}

}
