package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Category;
import it.uniroma3.siw.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired 
	private CategoryRepository categoryRepository; 
	
	
	@Transactional
	public Category newCategoty(Category category) {
		//il validatore controlla
		return this.categoryRepository.save(category); 
	}
	
	@Transactional
	public Category saveCategory(Category category) {
		//il validatore controlla
		return this.categoryRepository.save(category); 
	}
	
	@Transactional
	public List<Category> getAllCategories() {
		List<Category> categories = new LinkedList<>(); 
		for(Category cat : this.categoryRepository.findAll()) categories.add(cat); 
		return categories; 
	}

	public Category getCategory(Long id) {
		return this.categoryRepository.findById(id).get();
	}

	public boolean existsByName(String name) {
		return this.categoryRepository.existsByName(name);
	}
}
