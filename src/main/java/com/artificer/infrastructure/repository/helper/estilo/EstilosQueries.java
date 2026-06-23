package com.artificer.infrastructure.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.domain.model.Estilo;

public interface EstilosQueries {

	Page<Estilo> filtrar(String nome, Pageable pageable);

}
