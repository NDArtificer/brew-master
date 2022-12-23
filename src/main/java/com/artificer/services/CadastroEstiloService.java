package com.artificer.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.artificer.exceptions.CervejaEmUsoException;
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
	public Estilo save(Estilo estilo) {
		Optional<Estilo> estiloExistente = estiloRepository.findByNome(estilo.getNome());
		if (estiloExistente.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Já existe um estilo cadastrado com esse nome!");
		}
		return estiloRepository.saveAndFlush(estilo);
	}

	public void excluir(Estilo estilo) {
		try {

			estiloRepository.delete(estilo);
			estiloRepository.flush();
		} catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new CervejaEmUsoException("Falha ao excluir, Cerveja informada está em uso!");
		}

	}

}
