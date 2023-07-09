package it.uniroma3.siw.controller;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.service.CategoryService;
import it.uniroma3.siw.service.RecipeService;

@Controller
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	RecipeService recipeService;
	
	@GetMapping("/categories")
	public String getAllCategories(Model model) {
		 model.addAttribute("categories", this.categoryService.getAllCategories());
		 return "all/categories.html";
	}
	
	@GetMapping("/category/{id}")
	public String getCategory (@PathVariable("id") Long id, Model model) {
		model.addAttribute("category", this.categoryService.getCategory(id));
		Set<Recipe> set = this.categoryService.getCategory(id).getRicette();
		return "all/categoryRecipes.html";
	}

}
