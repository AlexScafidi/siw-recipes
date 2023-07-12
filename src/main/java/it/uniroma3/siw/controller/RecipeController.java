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
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.IngredientQuantity;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.service.CategoryService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.FileStorageService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.service.IngredientQuantityService;
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
	@Autowired
	private ImageService imageService;
	@Autowired
	private FileStorageService storageService; 
	
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
	
	@GetMapping(value="/recipe/{recipeId}")
	public String recipe(@PathVariable("recipeId") Long recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.getRecipe(recipeId));
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
	public String newRecipe(Model model, HttpSession httpSession, @RequestParam("userDateils") UserDetails userDetails) {
		
		//associo la ricetta al suo autore
		model.addAttribute("recipe",this.recipeService.newRecipe((Recipe)httpSession.getAttribute("recipe"),userDetails));
		//altrimenti
		return "all/recipe.html";
	}
	
	@PostMapping(value="/user/ingredientsToAdd")
	public String showAllIngredientToAdd(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult recipeBindingResult, 
			Model model, HttpSession httpSession, @RequestParam("file") MultipartFile file) {
		this.recipeValidator.validate(recipe,recipeBindingResult); 
		if(recipeBindingResult.hasErrors()) {
			model.addAttribute("recipe", recipe); 
			model.addAttribute("categories", this.categoryService.getAllCategories()); 
			return "user/formNewRecipe.html"; 
		}
		if(file.getSize() != 0) {
			Image image = this.storageService.createImage(file);
			recipe.setPicture(image);
			this.imageService.save(image);
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
	
//	/**
//	 * GET : pagina con la form per inserire una nuova ricetta
//	 * @param model
//	 * @return
//	 */
//	@GetMapping(value="/user/formNewRecipe")
//	public String formNewRecipe(Model model,HttpSession httpSession) {
//		Recipe recipe = new Recipe(); 
//		httpSession.setAttribute("recipe", recipe);
//		model.addAttribute("recipe",recipe);
//		//model.addAttribute("categories", this.categoryService.getAllCategories());
//		return "older/formNewRecipe.html"; 
//	}
//	
//	/**
//	 * GET : per andare alla pagina con tutti gli ingredienti
//	 * @param recipe
//	 * @param model
//	 * @return
//	 */
//	@PostMapping(value="/user/ingredientsToAdd")
//	public String ingredientsToAdd(Model model, HttpSession httpSession) {
//		Recipe recipe = (Recipe)httpSession.getAttribute("recipe"); 
//		model.addAttribute("recipe",recipe); 
//		model.addAttribute("ingredientsQuantity", recipe.getQuantityIngredients()); 
//		model.addAttribute("ingredientsToAdd", this.ingredientService.getAllIngredientsNotInRecipeNoRepo(recipe)); 
//		return "older/ingredientsToAdd.html"; 
//	}
//	
//	/**
//	 * GET : per tornare alla pagina con la form per inserire una nuova ricetta
//	 * @param recipe
//	 * @param model
//	 * @return
//	 * 
//	 */
//	@PostMapping(value="/user/backToFormNewRecipe")
//	public String backToFormNewRecipe(Model model, HttpSession httpSession) {
//		Recipe recipe = (Recipe)httpSession.getAttribute("recipe"); 
//		System.out.println(recipe.getTitle()); 
//		model.addAttribute("recipe",recipe);
//	//	model.addAttribute("categories", this.categoryService.getAllCategories());
//		return "older/formNewRecipe.html"; 
//	}
//	
//	/**
//	 * POST : per inserire un ingrediente nella ricetta
//	 * @param ingredientQuantity
//	 * @param IngredientQuantityBindingResult
//	 * @param ingredientId
//	 * @param model
//	 * @param recipe
//	 * @param httpSession
//	 * @return
//	 */
//	@PostMapping(value="/user/addIngredientWithQuantity/{ingredientId}")
//	public String addIngredientQuantity(@Valid @ModelAttribute("ingredientQuantity") IngredientQuantity ingredientQuantity, BindingResult IngredientQuantityBindingResult, @PathVariable("ingredientId")Long ingredientId, Model model,@ModelAttribute("recipe") Recipe recipe, HttpSession httpSession) {
//		Ingredient ingredient = this.ingredientService.GetIngredientById(ingredientId); 
//		if(ingredientQuantity == null || ingredient == null) return "paginaError.html";
//		//Recipe recipe = (Recipe)httpSession.getAttribute("recipe"); 
//
//		if(IngredientQuantityBindingResult.hasErrors()) {
//			model.addAttribute("ingredient",ingredient); 
//			model.addAttribute("recipe",recipe); 
//			model.addAttribute("misures",IngredientQuantity.misures);
//			return "older/formAddIngredientWithQuantity.html";}
//		//se tutto bene 
//		model.addAttribute("recipe",this.recipeService.addIngredient(recipe,ingredient,ingredientQuantity)); 
//		model.addAttribute("ingredientsQuantity", recipe.getQuantityIngredients()); 
//		model.addAttribute("ingredientsToAdd", this.ingredientService.getAllIngredientsNotInRecipeNoRepo(recipe)); 
//		return "older/ingredientsToAdd.html"; 
//	}
//	
//	@GetMapping(value="/user/removeIngredientWithQuantity/{ingredientName}")
//	public String deleteIngredientQuantity(@PathVariable("ingredientName") String ingredientName, Model model,@ModelAttribute("recipe") Recipe recipe, HttpSession httpSession) {
//		if(ingredientName == null) return "paginaError.html"; 
//		//Recipe recipe = (Recipe)httpSession.getAttribute("recipe"); 
//		model.addAttribute("recipe",this.recipeService.deleteIngredientNoRepo(recipe,ingredientName)); 
//		model.addAttribute("ingredientsQuantity", recipe.getQuantityIngredients()); 
//		model.addAttribute("ingredientsToAdd", this.ingredientService.getAllIngredientsNotInRecipeNoRepo(recipe)); 
//		return "older/ingredientsToAdd.html"; 
//	}

	
	@GetMapping("/admin/deleteRecipe/{recipeId}")
	public String deleteRecipe(@PathVariable("recipeId") Long recipeId, Model model) {
		this.recipeService.deleteRecipe(recipeId);
		model.addAttribute("recipes", this.recipeService.getAllRecipe());
		return "all/recipes.html";
	}


}
