package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Category;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.RecipeRepository;
import jakarta.transaction.Transactional;

@Service
public class RecipeService {

	@Autowired private RecipeRepository recipeRepository; 
	@Autowired private UserService userService; 
	@Autowired private CredentialsService credentialsService;
	@Autowired private FileStorageService fileStorageService;
	@Autowired private ImageService imageService;
	
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
		Category cat = this.getRecipe(id).getCategory();
		cat.getRicette().remove(this.getRecipe(id));
		this.recipeRepository.deleteById(id);
	}

	public Recipe getRecipe(Long id) {
		return this.recipeRepository.findById(id).get();
	}
	
	@Transactional
	public void updatePicture(Recipe rec, MultipartFile file) {
		if(file.getSize() != 0) {
			Image oldPic = rec.getPicture();
			if(oldPic != null) {
				String filename = oldPic.getFileName();
				this.fileStorageService.delete(filename);
				this.imageService.delete(oldPic.getId());
			}
			Image newPic = this.fileStorageService.createImage(file);
			rec.setPicture(newPic);
			this.imageService.save(newPic);
		}
	}
	
}
