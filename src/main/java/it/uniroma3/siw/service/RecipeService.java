package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.IngredientQuantity;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class RecipeService {

	@Autowired RecipeRepository recipeRepository; 
	@Autowired UserService userService; 
	@Autowired CredentialsService credentialsService;
	
	@Transactional
	public Recipe newRecipe(Recipe recipe) {
		//il validatore controlla
		return this.recipeRepository.save(recipe); 
	}
	
	@Transactional
	public Recipe saveRecipe(Recipe recipe) {
		//il validatore controlla
		return this.recipeRepository.save(recipe); 
	}
	
	@Transactional
	public List<Recipe> getAllRecipe() {
		List<Recipe> recipes = new LinkedList<>(); 
		for(Recipe rec : this.recipeRepository.findAll())  recipes.add(rec); 
		return recipes; 
	}
	
	@Transactional
	public List<Recipe> getAllNewRecipe() {
		List<Recipe> recipes = new LinkedList<>(); 
		for(Recipe rec : this.recipeRepository.findAllNewRecipes())  recipes.add(rec); 
		return recipes; 
	}

	public Recipe createNewRecipe(Recipe recipe, UserDetails userDetails) {
		//il controllo lo lascio alla validazione...gia fatta
		User user = (this.credentialsService.getCredentials(userDetails.getUsername())).getUser(); 
		if(user == null || recipe == null) return null; 
		//altrimenti
		user.getRecipes().add(recipe);
		recipe.setAuthor(user);
		//cascading
		this.userService.save(user); 
		return this.recipeRepository.save(recipe); 
	}
	
	@Transactional
	public void deleteRecipe(Long id) {
		this.recipeRepository.deleteById(id);
	}

	/**
	 * controllo che la stessa ricetta non sia già stata pubblicata dallo stesso autore
	 * @param recipe
	 * @return
	 */
	public boolean userAllreadyPublishedThisRecipe(Recipe recipe) {
		if(recipe == null) return false; 
		User author = recipe.getAuthor();
		return author != null && this.recipeRepository.existsByTitleAndAuthor(recipe.getTitle(),author); 
		
	}

	/**
	 * aggiunge gli ingredienti nella ricetta
	 * @param recipe
	 * @param ingredient
	 * @param ingredientQuantity
	 * @return
	 */
	public Recipe addIngredient(Recipe recipe, Ingredient ingredient, @Valid IngredientQuantity ingredientQuantity) {
		//il controllo è gia' stato fatto
		if(!this.ingredientAlreadyPresent(recipe,ingredient)) {
		ingredientQuantity.setIngredient(ingredient);
		recipe.getQuantityIngredients().add(ingredientQuantity);
		}
		return recipe; 
	}
	
	private boolean ingredientAlreadyPresent(Recipe recipe, Ingredient ingredient) {
		
		for(Ingredient i : recipe.getIngredients()) if(i.getName().equals(ingredient.getName())) return true; 
		return false; 
		
	}

	public Recipe deleteIngredientNoRepo(Recipe recipe, String ingredientName) {
		// TODO Auto-generated method stub
		Set<IngredientQuantity> ingrQ = recipe.getQuantityIngredients();
		for(IngredientQuantity iq : ingrQ) if(iq.getIngredient().getName().equals(ingredientName)) {
			ingrQ.remove(iq); break; 
		}
		
		return recipe; 
	}
	
}
