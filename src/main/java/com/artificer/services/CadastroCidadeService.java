package com.artificer.services;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
