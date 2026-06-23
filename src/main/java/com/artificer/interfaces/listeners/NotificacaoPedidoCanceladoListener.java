package com.artificer.interfaces.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.artificer.application.events.PedidoCanceladoEvent;
import com.artificer.domain.model.Pedido;
import com.artificer.application.services.EnvioEmailService;
import com.artificer.application.services.EnvioEmailService.Message;

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