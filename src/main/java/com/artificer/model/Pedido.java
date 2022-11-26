package com.artificer.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.artificer.model.enums.StatusVenda;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private LocalDateTime dataCriacao;
	@NumberFormat(pattern = "#,##0.00")
	@Column
	private BigDecimal valorFrete;
	@NumberFormat(pattern = "#,##0.00")
	@Column
	private BigDecimal valorDesconto;
	@Column
	private BigDecimal valorTotal;
	@Column
	private LocalDateTime dataHoraEntrega;

	@Column
	private String observacao;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@Enumerated(EnumType.STRING)
	private StatusVenda status = StatusVenda.ORCAMENTO;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens;

	@Transient
	private String uuid;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Transient
	private LocalDate dataEntrega;

	@DateTimeFormat(pattern = "HH:mm")
	@Transient
	private LocalTime horaEntrega;

	public boolean isNovoPedido() {
		return id == null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);
	}

	public void addItens(List<ItemPedido> itens) {
		this.itens = itens;
		this.itens.forEach(item -> item.setPedido(this));
	}

}
