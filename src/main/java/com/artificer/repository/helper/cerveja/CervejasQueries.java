package com.artificer.repository.helper.cerveja;

import java.util.List;

import com.artificer.model.Cerveja;
import com.artificer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	List<Cerveja> filtrar(CervejaFilter filter);

	//List<CervejaModel> porSkuOuNome(String skuOuNome);
}
