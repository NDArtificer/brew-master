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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.artificer.model.Cerveja;
import com.artificer.output.CervejaSummary;
import com.artificer.output.ValorItensEstoque;
import com.artificer.repository.filter.CervejaFilter;
import com.artificer.repository.paginacao.Pagination;
import com.artificer.services.FotoStorageService;

public class CervejasRepositoryImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private Pagination pagination;

	@Autowired
	private FotoStorageService storageService;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cerveja> criteria = builder.createQuery(Cerveja.class);
		Root<Cerveja> root = criteria.from(Cerveja.class);

		var predicate = addFilters(builder, filter, root);

		criteria.where(predicate.toArray(new Predicate[predicate.size()]));
		TypedQuery<Cerveja> query = (TypedQuery<Cerveja>) pagination.prepararOrdem(criteria, root, pageable);
		query = (TypedQuery<Cerveja>) pagination.prepararIntervalo(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Long total(CervejaFilter filter) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Cerveja> cervejaEntity = query.from(Cerveja.class);

		query.select(criteriaBuilder.count(cervejaEntity));
		var predicates = addFilters(criteriaBuilder, filter, cervejaEntity);
		query.where(predicates.toArray(new Predicate[predicates.size()]));

		return manager.createQuery(query).getSingleResult();
	}

	private List<Predicate> addFilters(CriteriaBuilder builder, CervejaFilter filter, Root<Cerveja> root) {
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
		return predicate;
	}

	private boolean isEstiloPresente(CervejaFilter filtro) {
		return filtro.getEstilo() != null && filtro.getEstilo().getId() != null;
	}

	@Override
	public List<CervejaSummary> porSkuOuNome(String skuOuNome) {
		String jpql = "select new com.artificer.output.CervejaSummary(id, sku, nome, origem, valor, foto) "
				+ "from Cerveja where lower(sku) like lower(:skuOuNome) or lower(nome) like lower(:skuOuNome)";
		List<CervejaSummary> cervejasFiltradas = manager.createQuery(jpql, CervejaSummary.class)
				.setParameter("skuOuNome", skuOuNome + "%").getResultList();

		cervejasFiltradas.forEach(cerveja -> {
			String thumbnailFoto = String.format("%s%s", FotoStorageService.THUMBNAIL_PREFIX, cerveja.getFoto());
			cerveja.setUrlThumbnailFoto(storageService.getUrl(thumbnailFoto));
		});
		return cervejasFiltradas;
	}

	@Override
	public ValorItensEstoque valorItensEstoque() {
		String query = "select new com.artificer.output.ValorItensEstoque(sum(valor * quantidadeEstoque), sum(quantidadeEstoque)) from Cerveja";
		return manager.createQuery(query, ValorItensEstoque.class).getSingleResult();
	}

}
