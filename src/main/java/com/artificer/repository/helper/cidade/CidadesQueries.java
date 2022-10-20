package com.artificer.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.model.Cidade;
import com.artificer.repository.filter.CidadeFilter;

public interface CidadesQueries {

	Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable);
}
