package com.artificer.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.artificer.model.Grupo;
@Component
public class GrupoConverter implements Converter<String, Grupo> {

	@Override
	public Grupo convert(String id) {
		if (StringUtils.hasText(id)) {
			Grupo grupo = new Grupo();
			grupo.setId(Long.valueOf(id));

			return grupo;
		}
		return null;
	}

}
