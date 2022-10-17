package com.artificer.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.artificer.model.Estado;

public class EstadoConverter implements Converter<String, Estado> {

	@Override
	public Estado convert(String id) {
		if (StringUtils.hasText(id)) {
			Estado estado = new Estado();
			estado.setId(Long.valueOf(id));

			return estado;
		}
		return null;
	}

}
