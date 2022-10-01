package com.artificer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.artificer.services.FotoStorageService;

@Component
public class CervejaListener {

	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@EventListener(condition = "#event.temFoto()")
	public void cervejaSalva(CervejaSalvaEvent event) {
		fotoStorage.salvar(event.getCerveja().getFoto());
	}
}
