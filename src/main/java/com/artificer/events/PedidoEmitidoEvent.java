package com.artificer.events;

import com.artificer.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoEmitidoEvent {

	private Pedido pedido;
}