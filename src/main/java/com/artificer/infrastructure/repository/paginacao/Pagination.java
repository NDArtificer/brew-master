package com.artificer.infrastructure.repository.paginacao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class Pagination {

	@PersistenceContext
	private EntityManager manager;

	public <T> TypedQuery<T> prepararIntervalo(TypedQuery<T> typedQuery, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		typedQuery.setFirstResult(primeiroRegistro);
		typedQuery.setMaxResults(totalRegistrosPorPagina);

		return typedQuery;
	}

	public <T> TypedQuery<T> prepararOrdem(CriteriaQuery<T> query, Root<T> fromEntity, Pageable pageable) {
		Sort sort = pageable.getSort();

		if (sort != null && sort.isSorted()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			Sort.Order sortOrder = sort.iterator().next();
			String property = sortOrder.getProperty();

			Order order = sortOrder.isAscending() ? builder.asc(fromEntity.get(property))
					: builder.desc(fromEntity.get(property));
			query.orderBy(order);
		}

		return manager.createQuery(query);
	}

}
