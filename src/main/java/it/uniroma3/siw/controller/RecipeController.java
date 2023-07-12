package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.IngredientQuantity;
import it.uniroma3.siw.service.IngredientQuantityService;
import it.uniroma3.siw.service.CategoryService;
import it.uniroma3.siw.service.CredentialsService;
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
	private RecipeValidator recipeValidator;
	@Autowired 
	private IngredientService ingredientService; 
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private IngredientQuantityService ingredientQuantityService;
	@Autowired
	private CredentialsService credentialsService; 
	
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken)
			return "all/recipe.html";
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("currentUser", credentials.getUser());
		return "all/recipe.html";
	}
	
	/**
	 * GET : PAGINA INIZIALE PER INSERIRE UNA NUOVA RICETTA NEL SISTEMA
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user/formNewRecipe")
	public String showFormNewRecipe(Model model) {
		Recipe recipe = new Recipe();
		model.addAttribute("recipe", recipe); 
		model.addAttribute("categories", this.categoryService.getAllCategories()); 
		
		return "user/formNewRecipe.html"; 
	}
	
	@PostMapping(value="/user/newRecipe")
	public String newRecipe(Model model, HttpSession httpSession) {
		
		//altrimenti
		return "all/recipe.html";
	}
	
	@PostMapping(value="/user/ingredientsToAdd")
	public String showAllIngredientToAdd(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult recipeBindingResult, Model model, HttpSession httpSession) {
		this.recipeValidator.validate(recipe,recipeBindingResult); 
		if(recipeBindingResult.hasErrors()) {
			model.addAttribute("recipe", recipe); 
	//		model.addAttribute("categories", this.categoryService.getAllCategories()); 
			return "user/formNewRecipe.html"; 
		}
		httpSession.setAttribute("recipe", recipe); 
		model.addAttribute("recipe",recipe); 
		model.addAttribute("ingredientsQuantity", recipe.getQuantityIngredients()); 
		model.addAttribute("ingredientsToAdd", this.ingredientService.getAllIngredientsNotInRecipeNoRepo(recipe)); 
		return "user/ingredientsToAdd.html"; 
	}
	
	@GetMapping(value="/user/addIngredientWithQuantity/{ingredientId}")
	public String showFormAddIngredient(@PathVariable("ingredientId") Long ingredientId, Model model, HttpSession httpSession) {
		Ingredient ingredient = this.ingredientService.GetIngredientById(ingredientId); 
		if(ingredient == null) return "paginaError.html"; 
		model.addAttribute("recipe",(Recipe)httpSession.getAttribute("recipe"));
		model.addAttribute("ingredient",ingredient); 
		model.addAttribute("ingredientQuantity",new IngredientQuantity());
		model.addAttribute("misures",IngredientQuantity.misures);
		return "user/formAddIngredientWithQuantity.html"; 
	}
	
	@PostMapping(value="/user/addIngredientWithQuantity/{ingredientId}")
	public String addIngredientQuantity(@Valid @ModelAttribute("ingredientQuantity") IngredientQuantity ingredientQuantity, BindingResult IngredientQuantityBindingResult, @PathVariable("ingredientId")Long ingredientId, Model model, HttpSession httpSession) {
		Ingredient ingredient = this.ingredientService.GetIngredientById(ingredientId); 
		if(ingredientQuantity == null || ingredient == null) return "paginaError.html";
		Recipe recipe = (Recipe)httpSession.getAttribute("recipe"); 

		if(IngredientQuantityBindingResult.hasErrors()) {
			model.addAttribute("ingredient",ingredient); 
			model.addAttribute("recipe",recipe); 
			model.addAttribute("misures",IngredientQuantity.misures);
			return "user/formAddIngredientWithQuantity.html";}
		//se tutto bene 
		model.addAttribute("recipe",this.recipeService.addIngredient(recipe,ingredient,ingredientQuantity)); 
		model.addAttribute("ingredientsQuantity", recipe.getQuantityIngredients()); 
		model.addAttribute("ingredientsToAdd", this.ingredientService.getAllIngredientsNotInRecipeNoRepo(recipe)); 
		return "user/ingredientsToAdd.html"; 
	}
	
	@GetMapping(value="/user/removeIngredientWithQuantity/{ingredientName}")
	public String deleteIngredientQuantity(@PathVariable("ingredientName") String ingredientName, Model model, HttpSession httpSession) {
		if(ingredientName == null) return "paginaError.html"; 
		Recipe recipe = (Recipe)httpSession.getAttribute("recipe"); 
		model.addAttribute("recipe",this.recipeService.deleteIngredientNoRepo(recipe,ingredientName)); 
		model.addAttribute("ingredientsQuantity", recipe.getQuantityIngredients()); 
		model.addAttribute("ingredientsToAdd", this.ingredientService.getAllIngredientsNotInRecipeNoRepo(recipe)); 
		return "user/ingredientsToAdd.html"; 
	}

	
	@GetMapping("/admin/deleteRecipe/{id}")
	public String deleteRecipe(@PathVariable("id") Long id, Model model) {
		this.recipeService.deleteRecipe(id);
		model.addAttribute("recipes", this.recipeService.getAllRecipe());
		return "all/recipes.html";
	}


}
