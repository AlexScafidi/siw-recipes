package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	boolean existsByName(String name);

	
}
