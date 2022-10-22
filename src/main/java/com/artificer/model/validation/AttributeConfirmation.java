package com.artificer.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import com.artificer.model.validation.validator.AttributeConfirmationValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AttributeConfirmationValidator.class })
public @interface AttributeConfirmation {

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Os atributos informados não conferem!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String attribute();

	String attributeConfirm();
}
