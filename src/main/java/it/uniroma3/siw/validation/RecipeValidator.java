package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.service.RecipeService;

@Component
public class RecipeValidator implements Validator {
	
	@Autowired private RecipeService recipeService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Recipe.class.equals(clazz); 
	}

	@Override
	public void validate(Object target, Errors errors) {
		//controllo ricette stesso titolo
		Recipe recipe = (Recipe)target; 
		if(recipe == null) return; 
		if(this.sameTitle(recipe)) errors.reject("recipe.duplicate.title");

	}
	
	private boolean sameTitle(Recipe recipe) {
		return this.recipeService.userAllreadyPublishedThisRecipe(recipe);  
	}

}
