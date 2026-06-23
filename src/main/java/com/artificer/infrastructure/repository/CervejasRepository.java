package com.artificer.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.artificer.infrastructure.projections.ValorItensEstoqueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.artificer.domain.model.Cerveja;
import com.artificer.infrastructure.repository.helper.cerveja.CervejasQueries;

@Repository
public interface CervejasRepository extends JpaRepository<Cerveja, Long>, CervejasQueries {

	Optional<Cerveja> findBySku(String sku);

	List<Cerveja> findBySkuStartingWithIgnoreCaseOrNomeStartingWithIgnoreCase(String sku, String nome);

	@Query("""
			select
			 COALESCE(sum(c.valor * c.quantidadeEstoque),0) as valor,
			 COALESCE(sum(c.quantidadeEstoque), 0) as totalItens
			 from Cerveja c""")
	ValorItensEstoqueProjection valorItensEstoque();

}
