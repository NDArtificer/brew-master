package com.artificer.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artificer.model.Cerveja;
import com.artificer.repository.CervejasRepository;

@Service
public class CadastroCervejaService {

	@Autowired
	private CervejasRepository repository;

	@Transactional
	public void save(Cerveja cerveja) {
		repository.save(cerveja);
	}

}
