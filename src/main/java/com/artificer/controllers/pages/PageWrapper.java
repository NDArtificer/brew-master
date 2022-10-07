package com.artificer.controllers.pages;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
