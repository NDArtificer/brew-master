package com.artificer.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artificer.model.Cliente;
import com.artificer.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional
	public void save(Cliente cliente) {
		clienteRepository.save(cliente);
	}

}
