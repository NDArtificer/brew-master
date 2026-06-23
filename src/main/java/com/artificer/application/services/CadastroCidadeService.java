package com.artificer.application.services;

import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.artificer.domain.exceptions.CervejaEmUsoException;
import com.artificer.domain.exceptions.CidadeJaCadastradaExeption;
import com.artificer.domain.model.Cidade;
import com.artificer.infrastructure.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Transactional
	public void save(@Valid Cidade cidade) {

		Optional<Cidade> cidadeExitente = cidadeRepository.findByNomeAndEstadoId(cidade.getNome(),
				cidade.getEstado().getId());
		if (cidadeExitente.isPresent()) {
			throw new CidadeJaCadastradaExeption("A cidade informada já foi cadastrada para esse estado!");
		}

		cidadeRepository.save(cidade);

	}

	@Transactional
	public void excluir(Cidade cidade) {

		try {

			cidadeRepository.delete(cidade);
			cidadeRepository.flush();
		} catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new CervejaEmUsoException("Falha ao excluir, Cerveja informada está em uso!");
		}

	}

}
