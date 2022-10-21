package com.artificer.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.artificer.model.Estado;

@Component
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
