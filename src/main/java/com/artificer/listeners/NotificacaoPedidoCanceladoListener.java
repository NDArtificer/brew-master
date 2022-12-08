package com.artificer.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.artificer.events.PedidoCanceladoEvent;
import com.artificer.model.Pedido;
import com.artificer.services.EnvioEmailService;
import com.artificer.services.EnvioEmailService.Message;

@Component
public class NotificacaoPedidoCanceladoListener {

	@Autowired
	private EnvioEmailService envioService;

	
	@TransactionalEventListener
	private void cancelarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();

		
		var message = Message.builder()
				.subject(pedido.getUsuario()
				.getNome() + " Pedido Cancelado")
				.body("pedido-cancelado.html")
				.model("pedido", pedido)
				.recipient(pedido.getCliente().getEmail())
				.build();
		
		envioService.enviar(message);
		
	}

}