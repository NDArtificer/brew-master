package com.artificer.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.artificer.exceptions.CervejaEmUsoException;
import com.artificer.exceptions.CpfCnpjClienteCadastroException;
import com.artificer.model.Cidade;
import com.artificer.model.Cliente;
import com.artificer.repository.CidadeRepository;
import com.artificer.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Transactional
	public void save(Cliente cliente) {

		if (cliente.isNovo()) {

			Optional<Cliente> clienteExistente = clienteRepository.findByCpfCnpj(cliente.getCpfCnpjSemFormatacao());
			if (clienteExistente.isPresent()) {
				throw new CpfCnpjClienteCadastroException("CPF/CNPJ informado já está cadastrado no banco de dados!");
			}
		}
		clienteRepository.save(cliente);
	}

	@Transactional
	public void excluir(Cliente cliente) {
		try {

			clienteRepository.delete(cliente);
			clienteRepository.flush();
		} catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new CervejaEmUsoException("Falha ao excluir, Cerveja informada está em uso!");
		}

	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public void clientComposeData(Cliente cliente) {
		if (cliente.getEndereco() == null || cliente.getEndereco().getCidade() == null
				|| cliente.getEndereco().getCidade().getId() == null) {
			return;
		}

		Cidade cidade = this.cidadeRepository.findByCodigoFetchingEstado(cliente.getEndereco().getCidade().getId());

		cliente.getEndereco().setCidade(cidade);
		cliente.getEndereco().setEstado(cidade.getEstado());
	}

}
