package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.IngredientRepository;
import jakarta.transaction.Transactional;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private ImageService imageService;
	
	@Transactional
	public Ingredient newIngredient(Ingredient ingredient) {
		//il validatore controlla
		return this.ingredientRepository.save(ingredient); 
	}
	
	@Transactional
	public Ingredient saveIngredient(Ingredient ingredient) {
		//il validatore controlla
		return this.ingredientRepository.save(ingredient); 
	}
	
	@Transactional
	public List<Ingredient> getAllIngredients() {
		List<Ingredient> ingredients = new LinkedList<>(); 
		for(Ingredient ing : this.ingredientRepository.findAll()) ingredients.add(ing); 
		return ingredients;
	}
	
	@Transactional
	public List<Ingredient> getAllIngredientsNotInRecipe(Recipe recipe) {
		List<Ingredient> ingredients = new LinkedList<>(); 
		for(Ingredient ing : this.ingredientRepository.findAllIngredientsNotInRecipe(recipe.getTitle())) ingredients.add(ing); 
		return ingredients;
	}
	
	@Transactional
	public void deleteIngredient(Long id) {
		this.ingredientRepository.deleteById(id);
	}

	public Ingredient getIngredient(Long id) {
		return this.ingredientRepository.findById(id).get();
	}
	
	@Transactional
	public void updatePicture(Ingredient ing, MultipartFile file) {
		if(file.getSize() != 0) {
			Image oldPic = ing.getPicture();
			if(oldPic != null) {
				String filename = oldPic.getFileName();
				this.fileStorageService.delete(filename);
				this.imageService.delete(oldPic.getId());
			}
			Image newPic = this.fileStorageService.createImage(file);
			ing.setPicture(newPic);
			this.imageService.save(newPic);
		}
	}

}
