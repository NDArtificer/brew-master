package com.artificer.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.artificer.events.PedidoEstoqueEvent;
import com.artificer.model.ItemPedido;
import com.artificer.model.Pedido;
import com.artificer.repository.PedidoRepository;

@Service
public class CadastroPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void salvar(Pedido pedido) {
		if (pedido.isNovoPedido()) {
			pedido.setDataCriacao(LocalDateTime.now());
		} else {
			Optional<Pedido> pedidoExistente = pedidoRepository.findById(pedido.getId());
			pedido.setDataCriacao(pedidoExistente.get().getDataCriacao());

			for (ItemPedido item : pedido.getItens()) {

				ItemPedido itemEditado = pedidoExistente.get().getItens().stream()
						.filter(itemFiltrado -> itemFiltrado.getCerveja().equals(item.getCerveja())).findFirst()
						.orElse(null);

				if (itemEditado == null) {
					continue;
				}

				item.setId(itemEditado.getId());

			}

		}

		if (pedido.getDataEntrega() != null) {
			pedido.setDataHoraEntrega(LocalDateTime.of(pedido.getDataEntrega(),
					pedido.getHoraEntrega() != null ? pedido.getHoraEntrega() : LocalTime.NOON));
		}
		pedidoRepository.save(pedido);
	}

	@Transactional
	public void enviarEmail(Pedido pedido) {
		pedido.emitirEnviarEmail();
		salvar(pedido);
		publisher.publishEvent(new PedidoEstoqueEvent(pedido));
	}

	@Transactional
	public void emitir(Pedido pedido) {
		pedido.emitir();
		salvar(pedido);
		publisher.publishEvent(new PedidoEstoqueEvent(pedido));
	}

	@PreAuthorize("#pedido.usuario == principal.usuario or hasAuthority('CANCELAR_PEDIDO')")
	@Transactional
	public void cancelar(Pedido pedido) {
		Optional<Pedido> pedidoExistente = pedidoRepository.findById(pedido.getId());
		pedidoExistente.get().cancelar();
		salvar(pedidoExistente.get());
		publisher.publishEvent(new PedidoEstoqueEvent(pedidoExistente.get()));
	}

}
