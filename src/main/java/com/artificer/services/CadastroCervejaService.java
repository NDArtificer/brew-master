package com.artificer.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.artificer.model.Cerveja;
import com.artificer.repository.CervejasRepository;
import com.artificer.service.event.cerveja.CervejaSalvaEvent;

@Service
public class CadastroCervejaService {

	@Autowired
	private CervejasRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void save(Cerveja cerveja) {
		repository.save(cerveja);

		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}

}
