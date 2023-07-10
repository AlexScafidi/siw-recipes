package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.service.CategoryService;
import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.validation.RecipeValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	@Autowired 
	private IngredientService ingredientService; 
	@Autowired
	private RecipeValidator recipeValidator;
	@Autowired
	private CategoryService categoryService; 
	@Autowired
	private HttpSession httpSession; 
	
	@GetMapping(value="/recipes")
	public String getAllRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.getAllRecipe());
		return "all/recipes.html";
	}
	
	/**
	 * GET : pagina con tutte le nuove ricette, indipendemente dalla categoria
	 * @param model
	 * @return
	 */
	@GetMapping(value="/recipes/newRecipes")
	public String getAllNewRecipes(Model model) {
		model.addAttribute("newRecipes", this.recipeService.getAllNewRecipe()); 
		return "all/newRecipes.html";
	}
	
	@GetMapping("/recipe/{id}")
	public String recipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", this.recipeService.getRecipe(id));
		return "all/recipe.html";
	}
	
	/**
	 * GET : PAGINA INIZIALE PER INSERIRE UNA NUOVA RICETTA NEL SISTEMA
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user/formNewRecipe")
	public String showFormNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe()); 
		model.addAttribute("categories", this.categoryService.getAllCategories()); 
		return "user/formNewRecipe.html"; 
	}
	
	@PostMapping(value="/user/newRecipe")
	public String newRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult recipeBindingResult, Model model) {

		this.recipeValidator.validate(recipe,recipeBindingResult); 
		if(recipeBindingResult.hasErrors()) return "user/formNewRecipe.html"; 
		
		//altrimenti
		return "";
	}
	
	@GetMapping(value="/user/returnFormNewRecipe")
	public String returnToFormNewRecipe(@ModelAttribute("recipe") Recipe recipe,Model model) {
		Recipe recipeNew = (Recipe) this.httpSession.getAttribute("recipe");
		model.addAttribute("recipe",recipeNew);
		return "user/formNewRecipe.html";
	}
	
//	/**
//	 * GET : controllo intermedio dei campi, se tutto ok -> redirezione altrimenti ritorno alla form
//	 * @param recipe
//	 * @param recipeBindingResult
//	 * @param model
//	 * @return
//	 */
//	@GetMapping(value="/user/checkFieldsNewRecipe")
//	public String checkFieldsRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult recipeBindingResult, Model model) {
//
//		this.recipeValidator.validate(recipe,recipeBindingResult); 
//		if(recipeBindingResult.hasErrors()) return "user/formNewRecipe.html"; 
//		
//		//altrimenti
//		return "redirect:/user/listIngredientToAdd";
//	}
	
	@PostMapping(value="/user/ingredientsToAdd")
	public String showAllIngredientToAdd(@ModelAttribute("recipe") Recipe recipe, Model model) {
		if(recipe == null) return "all/newRecipeError.html"; 
		System.out.println(recipe.getTitle());
		model.addAttribute("recipe", recipe); 
		model.addAttribute("ingredients", recipe.getIngredients()); 
		model.addAttribute("ingredientsToAdd", this.ingredientService.getAllIngredientsNotInRecipe(recipe)); 
		return "user/ingredientsToAdd.html"; 
	}
//	/**
//	 * DA COMPLETARE/MIGLIORARE
//	 * @param recipe
//	 * @param userDetails
//	 * @param RecipeBindingResult
//	 * @param model
//	 * @return
//	 */
//	@PostMapping(value="/user/formNewRecipe")
//	public String newRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, 
//			@RequestParam("userDetails") UserDetails userDetails, 
//			BindingResult RecipeBindingResult, Model model) {
//		
//		//controllo di validazione
//		if(RecipeBindingResult.hasErrors()) {return "user/formNewRecipe.html";}
//		
//		//altrimenti salvo,associo all'utente e la mostro la ricetta nella sua pagina
//		
//		model.addAttribute("recipe", this.recipeService.createNewRecipe(recipe,userDetails)); 
//		return "all/recipe.html"; 
//	}
	
	@GetMapping("/admin/deleteRecipe/{id}")
	public String deleteRecipe(@PathVariable("id") Long id, Model model) {
		this.recipeService.deleteRecipe(id);
		model.addAttribute("recipes", this.recipeService.getAllRecipe());
		return "all/recipes.html";
	}

}
