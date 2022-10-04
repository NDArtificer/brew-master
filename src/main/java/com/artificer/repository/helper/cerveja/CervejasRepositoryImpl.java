package com.artificer.repository.helper.cerveja;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.artificer.model.Cerveja;
import com.artificer.repository.filter.CervejaFilter;

public class CervejasRepositoryImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cerveja> filtrar(CervejaFilter filter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cerveja> criteria = builder.createQuery(Cerveja.class);
		Root<Cerveja> root = criteria.from(Cerveja.class);

		var predicate = new ArrayList<Predicate>();

		if (filter != null) {
			if (StringUtils.hasText(filter.getSku())) {
				predicate.add(builder.equal(root.get("sku"), filter.getSku()));
			}

			if (StringUtils.hasText(filter.getNome())) {
				predicate.add(builder.like(root.get("nome"), "%" + filter.getNome() + "%"));
			}

			if (isEstiloPresente(filter)) {
				predicate.add(builder.equal(root.get("estilo"), filter.getEstilo().getId()));
			}

			if (filter.getSabor() != null) {
				predicate.add(builder.equal(root.get("sabor"), filter.getSabor()));
			}

			if (filter.getOrigem() != null) {
				predicate.add(builder.equal(root.get("origem"), filter.getOrigem()));
			}

			if (filter.getValorDe() != null) {
				predicate.add(builder.greaterThanOrEqualTo(root.get("valor"), filter.getValorDe()));
			}

			if (filter.getValorAte() != null) {
				predicate.add(builder.lessThanOrEqualTo(root.get("valor"), filter.getValorAte()));
			}
		}

		criteria.where(predicate.toArray(new Predicate[predicate.size()]));
		TypedQuery<Cerveja> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private boolean isEstiloPresente(CervejaFilter filtro) {
		return filtro.getEstilo() != null && filtro.getEstilo().getId() != null;
	}

//	@Override
//	public List<CervejaModel> porSkuOuNome(String skuOuNome) {
//		String jpql = "select new com.algaworks.brewer.dto.CervejaDTO(codigo, sku, nome, origem, valor, foto) "
//				+ "from Cerveja where lower(sku) like lower(:skuOuNome) or lower(nome) like lower(:skuOuNome)";
//		List<CervejaModel> cervejasFiltradas = manager.createQuery(jpql, CervejaModel.class)
//					.setParameter("skuOuNome", skuOuNome + "%")
//					.getResultList();
//		return cervejasFiltradas;
//	}

}
