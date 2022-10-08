package com.artificer.repository.helper.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.model.Cerveja;
import com.artificer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);

	//List<CervejaModel> porSkuOuNome(String skuOuNome);
}
