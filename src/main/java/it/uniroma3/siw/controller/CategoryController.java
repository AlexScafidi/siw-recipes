package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Category;
import it.uniroma3.siw.service.CategoryService;
import it.uniroma3.siw.validation.CategoryValidator;
import jakarta.validation.Valid;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryValidator categoryValidator;
	
	@GetMapping("/categories")
	public String getAllCategories(Model model) {
		 model.addAttribute("categories", this.categoryService.getAllCategories());
		 return "all/categories.html";
	}
	
	@GetMapping("/category/{id}")
	public String getCategory (@PathVariable("id") Long id, Model model) {
		model.addAttribute("category", this.categoryService.getCategory(id));
		return "all/categoryRecipes.html";
	}
	
	@GetMapping("/admin/formNewCategory")
	public String formNewCategory (Model model) {
		model.addAttribute("category", new Category());
		return "admin/formNewCategory.html";
	}
	
	@PostMapping("/admin/formNewCategory")
	public String newCategory (@Valid @ModelAttribute("category") Category cat, BindingResult bindRes, Model model) {
		this.categoryValidator.validate(cat, bindRes);
		if(!bindRes.hasErrors()) {
			this.categoryService.saveCategory(cat);
			model.addAttribute("categories", this.categoryService.getAllCategories());
			model.addAttribute("nomeCat", cat.getName());
			return "all/categories.html";
		}
		return "admin/formNewCategory.html";
	}
	
	@GetMapping("/admin/updateCategory/{id}")
	public String formUpdateCategory (@PathVariable("id") Long id, Model model) {
		model.addAttribute("category", this.categoryService.getCategory(id));
		return "admin/formUpdateCategory.html";
	}
	
	@PostMapping("/admin/updateCategory/{id}")
	public String updateCategory (@PathVariable("id") Long id, @ModelAttribute("category") Category cat, Model model) {
		Category old = this.categoryService.getCategory(id);
		old.setDescription(cat.getDescription());
		old.setName(cat.getName());
		this.categoryService.saveCategory(old);
		model.addAttribute("categories", this.categoryService.getAllCategories());
		return "all/categories.html";
	}

}
