package com.artificer.infrastructure.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.artificer.domain.model.Grupo;
import com.artificer.domain.model.Usuario;
import com.artificer.infrastructure.repository.filter.UsuarioFilter;
import com.artificer.infrastructure.repository.paginacao.Pagination;

public class UsuarioRepositoryImpl implements UsuariosQueries {

	@Autowired
	private EntityManager manager;

	@Autowired
	private Pagination pagination;

	@Override
	public Optional<Usuario> findByEmailAndAtivo(String email) {
		return manager.createQuery(
						"from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
				.setParameter("email", email)
				.getResultList()
				.stream()
				.findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager.createQuery(
						"select distinct p.nome " +
								"from Usuario u " +
								"join u.grupos g " +
								"join g.permissoes p " +
								"where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class).distinct(true);
		Root<Usuario> root = cq.from(Usuario.class);

		List<Predicate> predicates = criarPredicados(cb, filter, root);
		cq.where(cb.and(predicates.toArray(new Predicate[0])));

		TypedQuery<Usuario> query = pagination.prepararOrdem(cq, root, pageable);
		query = pagination.prepararIntervalo(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, contarTotal(filter));
	}

	private Long contarTotal(UsuarioFilter filter) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Usuario> root = cq.from(Usuario.class);

		cq.select(cb.count(root));
		List<Predicate> predicates = criarPredicados(cb, filter, root);
		cq.where(predicates.toArray(new Predicate[0]));

		return manager.createQuery(cq).getSingleResult();
	}

	private List<Predicate> criarPredicados(CriteriaBuilder cb, UsuarioFilter filter, Root<Usuario> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filter != null) {
			if (StringUtils.hasText(filter.getNome())) {
				predicates.add(cb.like(cb.lower(root.get("nome")), "%%%s%%".formatted(filter.getNome().toLowerCase())));
			}
			if (StringUtils.hasText(filter.getEmail())) {
				predicates.add(cb.like(cb.lower(root.get("email")), "%%%s%%".formatted(filter.getEmail().toLowerCase())));
			}
			if (filter.getGrupos() != null && !filter.getGrupos().isEmpty()) {
				Join<Usuario, Grupo> joinGrupo = root.join("grupos");
				predicates.add(joinGrupo.get("id").in(filter.getGrupos().stream().map(Grupo::getId).toList()));
			}
		}
		return predicates;
	}

	@Override
	public Usuario findUserWithGroups(Long id) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class).distinct(true);
		Root<Usuario> root = cq.from(Usuario.class);
		root.fetch("grupos", JoinType.LEFT); // carrega grupos junto

		cq.where(cb.equal(root.get("id"), id));
		return manager.createQuery(cq).getSingleResult();
	}
}
