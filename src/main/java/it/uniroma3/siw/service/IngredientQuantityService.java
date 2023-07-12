package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.IngredientQuantity;
import it.uniroma3.siw.repository.IngredientQuantityRepository;
import jakarta.transaction.Transactional;

@Service
public class IngredientQuantityService {

	@Autowired
	private IngredientQuantityRepository ingredientQuantityRepository; 
	
	@Transactional
	public IngredientQuantity getIngredietQuantityById(Long ingredientQuantityId) {
		return this.ingredientQuantityRepository.findById(ingredientQuantityId).orElse(null); 
	}
}
