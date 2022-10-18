package com.artificer.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artificer.exceptions.CpfCnpjClienteCadastroException;
import com.artificer.model.Cliente;
import com.artificer.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional
	public void save(Cliente cliente) {

		Optional<Cliente> clienteExistente = clienteRepository.findByCpfCnpj(cliente.getCpfCnpjSemFormatacao());
		if (clienteExistente.isPresent()) {
			throw new CpfCnpjClienteCadastroException("CPF/CNPJ informado já está cadastrado no banco de dados!");
		}
		clienteRepository.save(cliente);
	}

}
