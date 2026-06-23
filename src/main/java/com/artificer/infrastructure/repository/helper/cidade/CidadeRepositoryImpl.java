package com.artificer.infrastructure.repository.helper.cidade;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.artificer.domain.model.Cidade;
import com.artificer.domain.model.Estado;
import com.artificer.infrastructure.repository.filter.CidadeFilter;
import com.artificer.infrastructure.repository.paginacao.Pagination;

public class CidadeRepositoryImpl implements CidadesQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private Pagination pagination;

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cidade> criteria = builder.createQuery(Cidade.class);
		Root<Cidade> root = criteria.from(Cidade.class);

		List<Predicate> predicates = addFilters(builder, filter, root);
		Join<Cidade, Estado> estadoCidade = root.join("estado");

		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		TypedQuery<Cidade> query = (TypedQuery<Cidade>) pagination.prepararOrdem(criteria, root, pageable);
		query = (TypedQuery<Cidade>) pagination.prepararIntervalo(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Long total(CidadeFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Cidade> root = criteria.from(Cidade.class);

		criteria.select(builder.count(root));
		List<Predicate> predicates = addFilters(builder, filter, root);
		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		return manager.createQuery(criteria).getSingleResult();
	}

	private List<Predicate> addFilters(CriteriaBuilder builder, CidadeFilter filter, Root<Cidade> root) {
		var predicate = new ArrayList<Predicate>();

		if (filter != null) {
			if (StringUtils.hasText(filter.getNome())) {
				predicate.add(builder.like(root.get("nome"), "%" + filter.getNome() + "%"));
			}

			if (isEstadoPresent(filter)) {
				predicate.add(builder.equal(root.get("estado"), filter.getEstado().getId()));
			}
		}

		return predicate;
	}

	private boolean isEstadoPresent(CidadeFilter filter) {
		// TODO Auto-generated method stub
		return filter.getEstado() != null && filter.getEstado().getId() != null;
	}

}
