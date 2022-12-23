package com.artificer.services;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.artificer.exceptions.CervejaEmUsoException;
import com.artificer.exceptions.CidadeJaCadastradaExeption;
import com.artificer.model.Cidade;
import com.artificer.repository.CidadeRepository;

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
