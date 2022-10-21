package com.artificer.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.artificer.model.Cidade;

@Component
public class CidadeConverter implements Converter<String, Cidade> {

	@Override
	public Cidade convert(String id) {
		if (StringUtils.hasText(id)) {
			Cidade cidade = new Cidade();
			cidade.setId(Long.valueOf(id));

			return cidade;
		}
		return null;
	}

}
