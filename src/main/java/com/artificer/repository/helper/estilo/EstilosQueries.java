package com.artificer.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.model.Estilo;

public interface EstilosQueries {

	Page<Estilo> filtrar(String nome, Pageable pageable);

}
