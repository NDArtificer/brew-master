package com.artificer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.artificer.thymeleaf.processor.ClassForErrorAttibruteTagProcessor;
import com.artificer.thymeleaf.processor.MessageElementTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect {

	public BrewerDialect() {
		super("AlgaWorks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttibruteTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		return processadores;
	}

}
