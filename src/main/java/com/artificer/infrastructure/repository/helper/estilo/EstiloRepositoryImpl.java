package com.artificer.infrastructure.repository.helper.estilo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.artificer.domain.model.Estilo;
import com.artificer.infrastructure.repository.paginacao.Pagination;

public class EstiloRepositoryImpl implements EstilosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private Pagination pagination;

	@Override
	public Page<Estilo> filtrar(String nome, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Estilo> criteriaQuery = criteriaBuilder.createQuery(Estilo.class);
		Root<Estilo> root = criteriaQuery.from(Estilo.class);

		var predicate = addFilters(criteriaBuilder, nome, root);
		criteriaQuery.where(predicate.toArray(new Predicate[0]));
		TypedQuery<Estilo> query = pagination.prepararOrdem(criteriaQuery, root, pageable);
		query = pagination.prepararIntervalo(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(nome));
	}

	private Long total(String nome) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Estilo> estiloEntity = query.from(Estilo.class);

		query.select(criteriaBuilder.count(estiloEntity));
		var predicates = addFilters(criteriaBuilder, nome, estiloEntity);
		query.where(predicates.toArray(new Predicate[0]));

		return manager.createQuery(query).getSingleResult();
	}

	private List<Predicate> addFilters(CriteriaBuilder criteriaBuilder, String nome, Root<Estilo> root) {
		var predicate = new ArrayList<Predicate>();

		if (StringUtils.hasText(nome)) {
			predicate.add(criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
		}
		return predicate;
	}

}
