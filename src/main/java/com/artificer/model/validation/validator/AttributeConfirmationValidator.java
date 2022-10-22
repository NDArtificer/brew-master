package com.artificer.model.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import com.artificer.model.validation.AttributeConfirmation;

import lombok.val;

public class AttributeConfirmationValidator implements ConstraintValidator<AttributeConfirmation, Object> {

	private String atributo;
	private String atributoConfirmacao;

	@Override
	public void initialize(AttributeConfirmation constraintAnnotation) {
		this.atributo = constraintAnnotation.attribute();
		this.atributoConfirmacao = constraintAnnotation.attributeConfirm();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean isValid = false;
		try {
			Object valorAtributo = BeanUtils.getProperty(value, this.atributo);
			Object valorAtributoConfirmacao = BeanUtils.getProperty(value, this.atributoConfirmacao);

			isValid = bothNull(valorAtributo, valorAtributoConfirmacao)
					|| bothEquals(valorAtributo, valorAtributoConfirmacao);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao Recuperar os valores do atributos!", e);
			// TODO: handle exception
		}
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			String message = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(message);
			violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
		}

		return isValid;
	}

	private boolean bothEquals(Object valorAtributo, Object valorAtributoConfirmacao) {
		// TODO Auto-generated method stub
		return valorAtributo != null && (valorAtributo.equals(valorAtributoConfirmacao));
	}

	private boolean bothNull(Object valorAtributo, Object valorAtributoConfirmacao) {

		return valorAtributo == null && valorAtributoConfirmacao == null;
	}

}
