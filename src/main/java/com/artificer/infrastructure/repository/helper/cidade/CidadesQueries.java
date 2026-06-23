package com.artificer.infrastructure.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.domain.model.Cidade;
import com.artificer.infrastructure.repository.filter.CidadeFilter;

public interface CidadesQueries {

	Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable);
}
