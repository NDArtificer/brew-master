package com.artificer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.artificer.model.Cerveja;
import com.artificer.output.CervejaSummary;
import com.artificer.output.ValorItensEstoque;
import com.artificer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);

	List<CervejaSummary> porSkuOuNome(String skuOuNome);

	ValorItensEstoque valorItensEstoque();
}
