package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.service.FileStorageService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.validation.IngredientValidator;
import jakarta.validation.Valid;

@Controller
public class IngredientController {
	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private IngredientValidator ingredientValidator;
	@Autowired
	private FileStorageService storageService;
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/user/formNewIngredient")
	public String formNewIngredient(Model model) {
		model.addAttribute("ingredient", new Ingredient());
		return "user/formNewIngredient.html";
	}
	
	@PostMapping("/user/formNewIngredient")
	public String newIngredient(@Valid @ModelAttribute("ingredient") Ingredient ing, 
			BindingResult bindRes, Model model, @RequestParam("file") MultipartFile file) {
		this.ingredientValidator.validate(ing, bindRes);
		if(!bindRes.hasErrors()) {
			if(file.getSize() != 0) {
				Image image = this.storageService.createImage(file);
				ing.setPicture(image);
				this.imageService.save(image);
			}
			this.ingredientService.saveIngredient(ing);
			model.addAttribute("nomeIng", ing.getName());
			return "all/index.html";
		}
		return "user/formNewIngredient.html";
	}
	
	@GetMapping("/ingredients")
	public String ingredients(Model model) {
		model.addAttribute("ingredients", this.ingredientService.getAllIngredients());
		return "all/ingredients.html";
	}
	
	@GetMapping("/admin/updateIngredient/{id}")
	public String formUpdateIngredient (@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingredient", this.ingredientService.getIngredient(id));
		return "admin/formUpdateIngredient.html";
	}
	
	@PostMapping("/admin/updateIngredient/{id}")
	public String updateIngredient (@PathVariable("id") Long id, @ModelAttribute("ingredient") Ingredient ing, 
			Model model, @RequestParam("file") MultipartFile file) {
		Ingredient oldIng = this.ingredientService.getIngredient(id);
		oldIng.setName(ing.getName());
		this.ingredientService.updatePicture(oldIng, file);
		this.ingredientService.saveIngredient(oldIng);
		model.addAttribute("ingredients", this.ingredientService.getAllIngredients());
		return "all/ingredients.html";
	}
}
