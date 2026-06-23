package com.artificer.application.events;

import com.artificer.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PedidoCanceladoEvent {

	private Pedido pedido;
}