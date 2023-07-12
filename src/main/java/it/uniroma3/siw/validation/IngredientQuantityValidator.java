package it.uniroma3.siw.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.IngredientQuantity;

public class IngredientQuantityValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return IngredientQuantity.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	}

}
