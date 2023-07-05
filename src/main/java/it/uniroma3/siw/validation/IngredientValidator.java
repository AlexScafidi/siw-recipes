package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.repository.IngredientRepository;

@Component
public class IngredientValidator implements Validator {
	
	@Autowired
	IngredientRepository ingRepo;

	@Override
	public boolean supports(Class<?> clazz) {
		return Ingredient.class.equals(clazz); 
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ingredient ing = (Ingredient) target;
		if(ing.getName() != null && ingRepo.existsByName(ing.getName()))
			errors.reject("ingredient.duplicate");
	}
}
