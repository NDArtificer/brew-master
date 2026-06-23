package com.artificer.application.events;

import com.artificer.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PedidoEmitidoEvent {

	private Pedido pedido;
}