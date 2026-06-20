package com.artificer.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.artificer.model.validation.validator.AttributeConfirmationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

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
