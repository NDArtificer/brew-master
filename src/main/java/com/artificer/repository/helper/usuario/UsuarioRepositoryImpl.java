package com.artificer.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.artificer.model.Grupo;
import com.artificer.model.Usuario;
import com.artificer.model.UsuarioGrupo;
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

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> filtrar(UsuarioFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		addFilters(filter, criteria);

		return criteria.list();
	}

	private void addFilters(UsuarioFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (StringUtils.hasText(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}

			if (StringUtils.hasText(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
			}

			criteria.createAlias("grupos", "g");
			if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for (Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getId).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.id", codigoGrupo));
					dc.setProjection(Projections.property("id.usuario"));

					subqueries.add(Subqueries.propertyIn("id", dc));
				}

				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}

}
