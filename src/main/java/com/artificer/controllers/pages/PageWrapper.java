package com.artificer.controllers.pages;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
		this.page = page;
		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
	}

	public String urlParaPagina(int pagina) {
		return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}

	public String urlOrdenada(String property) {
		var uriBuilderOrder = UriComponentsBuilder.fromUriString(uriBuilder.build(true).encode().toUriString());
		String orderType = String.format("%s,%s", property, inverterDirecao(property));
		return uriBuilderOrder.replaceQueryParam("sort", orderType).build(true).encode().toUriString();
	}

	public String inverterDirecao(String property) {
		String orderType = "asc";

		Order order = getOrder(property);
		if (order != null) {
			orderType = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}

		return orderType;
	}

	public boolean descendente(String property) {
		return inverterDirecao(property).equals("asc");
	}

	public boolean ordenada(String property) {
		var order = getOrder(property);

		if (order == null) {
			return false;
		}

		return page.getSort().getOrderFor(property) != null ? true : false;
	}

	private Order getOrder(String property) {

		return page.getSort() != null ? page.getSort().getOrderFor(property) : null;
	}

	public List<T> getConteudo() {
		return page.getContent();

	}

	public boolean isVazia() {
		return page.getContent().isEmpty();
	}

	public int getAtual() {
		return page.getNumber();
	}

	public boolean isPrimeira() {
		return page.isFirst();
	}

	public boolean isUltima() {
		return page.isLast();
	}

	public int getTotal() {
		return page.getTotalPages();
	}

}
