package com.artificer.services;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.artificer.exceptions.CervejaEmUsoException;
import com.artificer.model.Cerveja;
import com.artificer.repository.CervejasRepository;
import com.artificer.service.event.cerveja.CervejaSalvaEvent;

@Service
public class CadastroCervejaService {

	@Autowired
	private CervejasRepository repository;

	@Autowired
	private FotoStorageService fotoStorage;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void save(Cerveja cerveja) {
		repository.save(cerveja);
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}

	@Transactional
	public Cerveja buscar(Long id) {

		return repository.findById(id).orElseThrow(() -> new RuntimeException(
				String.format("Não existe uma cerveja para o id %s cadastrada na base de dados!", id)));
	}

	@Transactional
	public void excluir(Cerveja cerveja) {

		try {
			repository.delete(cerveja);
			repository.flush();

			fotoStorage.excluir(cerveja.getFoto());
		} catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new CervejaEmUsoException("Falha ao excluir, Cerveja informada está em uso!");
		}

	}

}
