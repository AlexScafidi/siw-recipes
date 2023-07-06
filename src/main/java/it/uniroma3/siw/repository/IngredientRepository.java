package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

	@Query(nativeQuery = true, 
			value="select * from ingredient i where i.id not in \r\n"
					+ "(select ii.id from recipe r \r\n"
					+ " join recipe_quantity_ingredients rqi on r.id = rqi.recipe_id \r\n"
					+ " join ingredient_quantity qi on rqi.quantity_ingredients_id = qi.id \r\n"
					+ " join ingredient ii on qi.ingredient_id = ii.id \r\n"
					+ " where r.title = :recipe_title)   ")
public Iterable<Ingredient> findAllIngredientsNotInRecipe(String recipe_title);
	

}
