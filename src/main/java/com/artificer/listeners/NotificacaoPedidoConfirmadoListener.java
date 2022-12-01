package com.artificer.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.artificer.events.PedidoEmitidoEvent;
import com.artificer.model.Pedido;
import com.artificer.services.EnvioEmailService;
import com.artificer.services.EnvioEmailService.Message;

@Component
public class NotificacaoPedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService envioService;

	@TransactionalEventListener
	private void confimarPedido(PedidoEmitidoEvent event) {
		Pedido pedido = event.getPedido();

		var message = Message.builder()
				.subject(pedido.getUsuario()
				.getNome() + " Pedido Emitido")
				.body("pedido-confirmado.html")
				.model("pedido", pedido)
				.recipient(pedido.getCliente().getEmail())
				.build();
		
		envioService.enviar(message);
	}

}