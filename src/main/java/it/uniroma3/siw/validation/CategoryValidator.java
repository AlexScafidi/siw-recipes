package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Category;
import it.uniroma3.siw.service.CategoryService;

@Component
public class CategoryValidator implements Validator {
	
	@Autowired
	CategoryService catService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Category.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Category cat = (Category) target;
		if(cat.getName() != null && this.catService.existsByName(cat.getName()))
			errors.reject("category.duplicate");
		
	}

}
