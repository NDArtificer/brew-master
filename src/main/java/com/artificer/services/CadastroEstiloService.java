package com.artificer.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artificer.exceptions.NomeEstiloJaCadastradoException;
import com.artificer.exceptions.NomeEstiloNaoEncontradoException;
import com.artificer.model.Estilo;
import com.artificer.repository.EstiloRepository;

@Service
public class CadastroEstiloService {

	@Autowired
	private EstiloRepository estiloRepository;

	@Transactional
	public Estilo findEstilo(Estilo estilo) {
		return estiloRepository.findByNome(estilo.getNome()).orElseThrow(
				() -> new NomeEstiloNaoEncontradoException("Não existe um estilo cadastrado com esse nome!"));
	}

	@Transactional
	public void save(Estilo estilo) {
		Optional<Estilo> estiloExistente = estiloRepository.findByNome(estilo.getNome());
		if (estiloExistente.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Já existe um estilo cadastrado com esse nome!");
		}
		estiloRepository.save(estilo);
	}

}
