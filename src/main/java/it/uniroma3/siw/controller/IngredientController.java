package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.validation.IngredientValidator;
import jakarta.validation.Valid;

@Controller
public class IngredientController {
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private IngredientValidator ingredientValidator;
	
	@GetMapping("/user/formNewIngredient")
	public String formNewIngredient(Model model) {
		model.addAttribute("ingredient", new Ingredient());
		return "user/formNewIngredient.html";
	}
	
	@PostMapping("/user/formNewIngredient")
	public String newIngredient(@Valid @ModelAttribute("ingredient") Ingredient ing, 
			BindingResult bindRes, Model model) {
		this.ingredientValidator.validate(ing, bindRes);
		if(!bindRes.hasErrors()) {
			this.ingredientService.saveIngredient(ing);
			model.addAttribute("nomeIng", ing.getName());
			return "all/index.html";
		}
		return "user/formNewIngredient.html";
	}
}
