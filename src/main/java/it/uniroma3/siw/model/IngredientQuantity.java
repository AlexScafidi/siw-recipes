package it.uniroma3.siw.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class IngredientQuantity {
	
	public final static String[] misures = {"N.D","Kg","g","etti","tazze","l","dl","cl","ml","cc"}; 
 	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	@NotBlank
	private String unitOfMeasure;
	@Column(nullable = false)
	@NotNull
	@Digits(integer=3, fraction=1)
	@DecimalMin(value = "0.0", inclusive = false)
	@DecimalMax(value = "1000.0", inclusive =  false)
	private BigDecimal quantity; 
	@ManyToOne(fetch = FetchType.EAGER)
	private Ingredient ingredient;
	
	public IngredientQuantity() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUnitOfMeasure() {
		return this.unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	} 
	
	@Override
	public String toString() {
		return this.getQuantity() + " " + this.getUnitOfMeasure();
	}
	
	
}
