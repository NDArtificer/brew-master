package com.artificer.repository.helper.cliente;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.artificer.model.Cidade;
import com.artificer.model.Cliente;
import com.artificer.model.Endereco;
import com.artificer.model.Estado;
import com.artificer.repository.filter.ClienteFilter;
import com.artificer.repository.paginacao.Pagination;

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
