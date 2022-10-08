package com.artificer.repository.helper.estilo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.artificer.model.Estilo;
import com.artificer.repository.paginacao.Pagination;

public class EstiloRepositoryImpl implements EstilosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private Pagination pagination;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Estilo> filtrar(String nome, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Estilo> criteriaQuery = criteriaBuilder.createQuery(Estilo.class);
		Root<Estilo> root = criteriaQuery.from(Estilo.class);

		var predicate = addFilters(criteriaBuilder, nome, root);
		criteriaQuery.where(predicate.toArray(new Predicate[predicate.size()]));
		TypedQuery<Estilo> query = (TypedQuery<Estilo>) pagination.prepararOrdem(criteriaQuery, root, pageable);
		query = (TypedQuery<Estilo>) pagination.prepararIntervalo(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(nome));
	}

	private Long total(String nome) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Estilo> estiloEntity = query.from(Estilo.class);

		query.select(criteriaBuilder.count(estiloEntity));
		var predicates = addFilters(criteriaBuilder, nome, estiloEntity);
		query.where(predicates.toArray(new Predicate[predicates.size()]));

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
