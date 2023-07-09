package it.uniroma3.siw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class IngredientQuantity {
	
	final public static String misures[] = {"N.D","Kg","g","etti","tazze","l","dl","cl","ml","cc"};
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	@NotBlank
	private String UnitOfmeasure;
	@Column(nullable = false)
	@NotBlank
	@Min(1)
	private Integer quantity;
	@ManyToOne(fetch = FetchType.EAGER)
	private Ingredient ingredient;
	
	public IngredientQuantity() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUnitOfmeasure() {
		return UnitOfmeasure;
	}
	public void setUnitOfmeasure(String unitOfmeasure) {
		UnitOfmeasure = unitOfmeasure;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	} 
	
	
}
