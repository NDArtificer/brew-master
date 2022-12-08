package com.artificer.events;

import com.artificer.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PedidoEmitidoEvent {

	private Pedido pedido;
}