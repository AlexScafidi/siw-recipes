package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.RecipeRepository;
import jakarta.transaction.Transactional;

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
	
}
