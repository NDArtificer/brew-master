package com.artificer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.model.Cliente;
import com.artificer.repository.helper.cliente.ClientesQueries;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClientesQueries {

	Optional<Cliente> findByCpfCnpj(String cpfCnpj);

}
