package com.artificer.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.artificer.model.Estilo;

public class EstiloConverter implements Converter<String, Estilo> {

	@Override
	public Estilo convert(String id) {
		if (StringUtils.hasText(id)) {
			Estilo estilo = new Estilo();
			estilo.setId(Long.valueOf(id));

			return estilo;
		}
		return null;
	}

}
