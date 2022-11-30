package com.artificer.controllers.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.artificer.model.Pedido;

@Component
public class PedidoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Pedido.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmpty(errors, "cliente.id", "", "Informe um cliente na pesquisa rápida!");

		Pedido pedido = (Pedido) target;
		if (pedido.getHoraEntrega() != null && pedido.getDataEntrega() == null) {
			errors.rejectValue("dataEntrega", "", "Informe uma data para o horário da entrega!");
		}
		if (pedido.getItens().isEmpty()) {
			errors.rejectValue("", "", "Pedido deve conter pelo menos um item!");
		}

		if (pedido.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
			errors.rejectValue("", "", "Valor total não pode ser menor que zero!");
		}

	}

}
