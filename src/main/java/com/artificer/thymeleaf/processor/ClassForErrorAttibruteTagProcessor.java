package com.artificer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;

public class ClassForErrorAttibruteTagProcessor extends AbstractAttributeTagProcessor {

	private static final String NOME_ATRIBUTO = "classforerror";
	private static final int PRECEDENCIA = 1000;

	public ClassForErrorAttibruteTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, NOME_ATRIBUTO, true, PRECEDENCIA, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		var hasError = FieldUtils.hasErrors(context, attributeValue);

		if(hasError) {
			var classesExistentes = tag.getAttributeValue("class");
			structureHandler.setAttribute("class", classesExistentes + " has-error");
		}
	}

}
