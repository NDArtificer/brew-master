package com.artificer.infrastructure.repository.helper.cerveja;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.domain.model.Cerveja;
import com.artificer.infrastructure.repository.filter.CervejaFilter;

public interface CervejasQueries {

	Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);

}
