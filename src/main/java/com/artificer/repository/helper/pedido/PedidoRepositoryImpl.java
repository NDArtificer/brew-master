package com.artificer.repository.helper.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
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
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.artificer.model.Cliente;
import com.artificer.model.ItemPedido;
import com.artificer.model.Pedido;
import com.artificer.model.enums.StatusVenda;
import com.artificer.model.enums.TipoPessoa;
import com.artificer.output.VendaMes;
import com.artificer.output.VendaOrigem;
import com.artificer.repository.filter.PedidoFilter;
import com.artificer.repository.paginacao.Pagination;

public class PedidoRepositoryImpl implements PedidoQueries {

	@Autowired
	private EntityManager manager;

	@Autowired
	private Pagination pagination;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Pedido> filtrar(PedidoFilter filter, Pageable pageable) {
		CriteriaBuilder criteria = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteria.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		root.alias("p");

		var predicate = addFilters(criteria, filter, root);

		Join<Pedido, Cliente> pedidoCidade = root.join("cliente");
		pedidoCidade.alias("c");

		criteriaQuery.where(predicate.toArray(new Predicate[predicate.size()]));
		TypedQuery<Pedido> query = (TypedQuery<Pedido>) pagination.prepararOrdem(criteriaQuery, root, pageable);
		query = (TypedQuery<Pedido>) pagination.prepararIntervalo(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Long total(PedidoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pedido> root = criteria.from(Pedido.class);
		root.alias("p");

		criteria.select(builder.count(root));

		Join<Pedido, Cliente> pedidoCidade = root.join("cliente");
		pedidoCidade.alias("c");

		List<Predicate> predicates = addFilters(builder, filter, root);
		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		return manager.createQuery(criteria).getSingleResult();
	}

	private List<Predicate> addFilters(CriteriaBuilder builder, PedidoFilter filter, Root<Pedido> root) {

		var predicate = new ArrayList<Predicate>();
		Join<Pedido, Cliente> pedidoCidade = root.join("cliente");
		pedidoCidade.alias("c");
		if (filter != null) {
			if (filter.getId() != null) {
				predicate.add(builder.equal(root.get("id"), filter.getId().toString()));

			}
			if (filter.getStatus() != null) {
				predicate.add(builder.equal(root.get("status"), filter.getStatus()));
			}

			if (filter.getDesde() != null) {
				LocalDateTime desde = LocalDateTime.of(filter.getDesde(), LocalTime.of(0, 0));
				predicate.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), desde));
			}

			if (filter.getAte() != null) {
				LocalDateTime ate = LocalDateTime.of(filter.getAte(), LocalTime.of(0, 0));
				predicate.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), ate));
			}

			if (filter.getValorMinimo() != null) {
				predicate.add(builder.greaterThanOrEqualTo(root.get("valorTotal"), filter.getValorMinimo()));
			}

			if (filter.getValorMaximo() != null) {
				predicate.add(builder.lessThanOrEqualTo(root.get("valorTotal"), filter.getValorMaximo()));
			}

			if (StringUtils.hasText(filter.getNomeCliente())) {
				predicate.add(builder.like(root.get("cliente").get("nome"), filter.getNomeCliente() + "%"));
			}
			if (StringUtils.hasText(filter.getCpfCnpjCliente())) {
				predicate.add(builder.equal(root.get("cliente").get("cpfCnpj"),
						TipoPessoa.removerFormatacao(filter.getCpfCnpjCliente())));
			}
		}
		return predicate;
	}

	@Transactional
	@Override
	public Pedido findWithItens(Long id) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class).distinct(true);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		root.alias("p");
		Join<Pedido, ItemPedido> pedidoItens = root.join("itens");
		pedidoItens.alias("i");
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
		TypedQuery<Pedido> query = manager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	@Override
	public BigDecimal valorTotalVendasAno() {
		String query = "select sum(valorTotal) FROM Pedido where year(dataCriacao) =:year and status =:status";
		Optional<BigDecimal> valor = Optional
				.ofNullable(manager.createQuery(query, BigDecimal.class).setParameter("year", Year.now().getValue())
						.setParameter("status", StatusVenda.EMITIDA).getSingleResult());
		return valor.orElse(BigDecimal.ZERO);

	}

	@Override
	public BigDecimal valorTotalVendasMes() {
		String query = "select sum(valorTotal) FROM Pedido where month(dataCriacao) =:month and status =:status";
		Optional<BigDecimal> valor = Optional.ofNullable(
				manager.createQuery(query, BigDecimal.class).setParameter("month", MonthDay.now().getMonthValue())
						.setParameter("status", StatusVenda.EMITIDA).getSingleResult());
		return valor.orElse(BigDecimal.ZERO);

	}

	@Override
	public BigDecimal valorTicketMedio() {
		String query = "select sum(valorTotal)/count(*) FROM Pedido where year(dataCriacao) =:year and status =:status";
		Optional<BigDecimal> valor = Optional
				.ofNullable(manager.createQuery(query, BigDecimal.class).setParameter("year", Year.now().getValue())
						.setParameter("status", StatusVenda.EMITIDA).getSingleResult());
		return valor.orElse(BigDecimal.ZERO);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaMes> totalVendaMes() {

		return manager.createNamedQuery("Vendas.totalPorMes").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaOrigem> totalVendaOrigem() {

		return manager.createNamedQuery("Vendas.porOrigem").getResultList();
	}

}
