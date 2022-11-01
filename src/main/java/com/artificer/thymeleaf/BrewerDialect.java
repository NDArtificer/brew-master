package com.artificer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.artificer.thymeleaf.processor.ClassForErrorAttibruteTagProcessor;
import com.artificer.thymeleaf.processor.MenuAttributeTagProcessor;
import com.artificer.thymeleaf.processor.MessageElementTagProcessor;
import com.artificer.thymeleaf.processor.OrderElementTagProcessor;

@Component
public class BrewerDialect extends AbstractProcessorDialect {

	public BrewerDialect() {
		super("AlgaWorks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttibruteTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		return processadores;
	}

}
