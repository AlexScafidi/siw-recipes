package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Category;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired 
	private CategoryRepository categoryRepository;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private ImageService imageService; 
	
	
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
	
	@Transactional
	public void updatePicture(Category cat, MultipartFile file) {
		if(file.getSize() != 0) {
			Image oldPic = cat.getPicture();
			if(oldPic != null) {
				String filename = oldPic.getFileName();
				this.fileStorageService.delete(filename);
				this.imageService.delete(oldPic.getId());
			}
			Image newPic = this.fileStorageService.createImage(file);
			cat.setPicture(newPic);
			this.imageService.save(newPic);
		}
	}
}
