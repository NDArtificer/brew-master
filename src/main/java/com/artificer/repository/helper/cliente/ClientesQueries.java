package com.artificer.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.model.Cliente;
import com.artificer.repository.filter.ClienteFilter;

public interface ClientesQueries {

	Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable);
}
