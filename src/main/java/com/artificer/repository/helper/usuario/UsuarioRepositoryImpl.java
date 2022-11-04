package com.artificer.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.artificer.model.Cliente;
import com.artificer.model.Grupo;
import com.artificer.model.Usuario;
import com.artificer.repository.filter.UsuarioFilter;
import com.artificer.repository.paginacao.Pagination;

public class UsuarioRepositoryImpl implements UsuariosQueries {

	@Autowired
	private EntityManager manager;

	@Autowired
	private Pagination pagination;

	@Override
	public Optional<Usuario> findByEmailAndAtivo(String email) {
		return manager.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {

		return manager.createQuery(
				"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario",
				String.class).setParameter("usuario", usuario).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class).distinct(true);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		root.alias("u");
		List<Predicate> predicates = addFilters(criteriaBuilder, filter, root, criteriaQuery);

		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		TypedQuery<Usuario> query = (TypedQuery<Usuario>) pagination.prepararOrdem(criteriaQuery, root, pageable);
		query = (TypedQuery<Usuario>) pagination.prepararIntervalo(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter, criteriaQuery));
	}

	private Long total(UsuarioFilter filter, CriteriaQuery<Usuario> criteriaQuery) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		root.alias("u");

		criteria.select(builder.count(root));
		List<Predicate> predicates = addFilters(builder, filter, root, criteriaQuery);
		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		return manager.createQuery(criteria).getSingleResult();
	}

	private List<Predicate> addFilters(CriteriaBuilder criteriaBuilder, UsuarioFilter filter, Root<Usuario> root,
			CriteriaQuery<Usuario> criteriaQuery) {
		var predicates = new ArrayList<Predicate>();

		if (filter != null) {
			if (StringUtils.hasText(filter.getNome())) {
				predicates.add(criteriaBuilder.like(root.get("nome"), filter.getNome()));
			}
			if (StringUtils.hasText(filter.getEmail())) {
				predicates.add(criteriaBuilder.like(root.get("email"), filter.getEmail()));
			}

			if (filter.getGrupos() != null && !filter.getGrupos().isEmpty()) {
				Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
				Root<Usuario> subRoot = subQuery.from(Usuario.class);
				Join<Usuario, Grupo> subQueryGrupo = subRoot.join("grupos");
				subRoot.alias("g");
				for (Long grupoId : filter.getGrupos().stream().mapToLong(Grupo::getId).toArray()) {
					subQuery.select(subRoot.get("id")).where(criteriaBuilder.equal(subQueryGrupo.get("id"), grupoId));
					predicates.add(criteriaBuilder.in(root.get("id")).value(subQuery));
				}

			}
		}

		return predicates;
	}

}
