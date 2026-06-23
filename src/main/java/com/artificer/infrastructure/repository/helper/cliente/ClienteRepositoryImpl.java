package com.artificer.infrastructure.repository.helper.cliente;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.artificer.domain.model.Cidade;
import com.artificer.domain.model.Cliente;
import com.artificer.domain.model.Endereco;
import com.artificer.domain.model.Estado;
import com.artificer.infrastructure.repository.filter.ClienteFilter;
import com.artificer.infrastructure.repository.paginacao.Pagination;

public class ClienteRepositoryImpl implements ClientesQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private Pagination pagination;

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
		Root<Cliente> root = criteria.from(Cliente.class);

		List<Predicate> predicates = addFilters(builder, filter, root);
		Join<Cliente, Endereco> enderecoCliente = root.join("endereco");
		Fetch<Endereco, Cidade> cidadeCliente = enderecoCliente.fetch("cidade", JoinType.LEFT);
		Fetch<Cidade, Estado> estadoCliente = cidadeCliente.fetch("estado", JoinType.LEFT);

		criteria.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Cliente> query = (TypedQuery<Cliente>) pagination.prepararOrdem(criteria, root, pageable);
		query = (TypedQuery<Cliente>) pagination.prepararIntervalo(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Long total(ClienteFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Cliente> root = criteria.from(Cliente.class);

		criteria.select(builder.count(root));
		List<Predicate> predicates = addFilters(builder, filter, root);
		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		return manager.createQuery(criteria).getSingleResult();
	}

	private List<Predicate> addFilters(CriteriaBuilder builder, ClienteFilter filter, Root<Cliente> root) {
		var predicate = new ArrayList<Predicate>();

		if (filter != null) {
			if (StringUtils.hasText(filter.getNome())) {
				predicate.add(builder.like(root.get("nome"), "%" + filter.getNome() + "%"));
			}

			if (StringUtils.hasText(filter.getCpfCnpj())) {
				predicate.add(builder.equal(root.get("cpfCnpj"), filter.getCpfCnpj()));
			}
		}

		return predicate;
	}

}
