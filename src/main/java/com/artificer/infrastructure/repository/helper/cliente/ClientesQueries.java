package com.artificer.infrastructure.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.domain.model.Cliente;
import com.artificer.infrastructure.repository.filter.ClienteFilter;

public interface ClientesQueries {

	Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable);
}
