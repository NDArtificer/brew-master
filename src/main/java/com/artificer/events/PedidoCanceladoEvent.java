package com.artificer.events;

import com.artificer.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PedidoCanceladoEvent {

	private Pedido pedido;
}