package it.uniroma3.siw.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 
	
	@Column(nullable = false, unique = true)
	@NotBlank
	private String title;
	@Column(nullable = false, length = 3000)
	@NotBlank
	private String preparationText;
	@Column(nullable = false, length = 3000)
	@NotBlank
	private String presentationText; 
	private boolean isNew; 
	@ManyToOne
	private User author; 
	@ManyToOne
	private Category category; 
	@OneToOne(cascade = CascadeType.ALL)
	private Image picture;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<IngredientQuantity> quantityIngredients; 
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "recipe_id")
	private List<Comment> comments;
	
	public Recipe() {
		this.isNew = true; 
		this.comments = new LinkedList<>(); 
		this.quantityIngredients = new HashSet<>(); 
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(Image picture) {
		this.picture = picture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	public String getPreparationText() {
		return preparationText;
	}

	public void setPreparationText(String preparationText) {
		this.preparationText = preparationText;
	}

	public String getPresentationText() {
		return presentationText;
	}

	public void setPresentationText(String presentationText) {
		this.presentationText = presentationText;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<IngredientQuantity> getQuantityIngredients() {
		return this.quantityIngredients;
	}

	public void setQuantityIngredients(Set<IngredientQuantity> quantityIngredients) {
		this.quantityIngredients = quantityIngredients;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Ritorna la media dei voti ricevuti alla ricetta
	 * @return
	 */
	public float getVote() {
		float vote = 0.0f;
		
		for(Comment comment : this.getComments()) vote+=comment.getVote();
		return vote/this.getComments().size(); 
	}
	
	/**
	 * ritorna tutti gli  ingredienti presenti nella ricetta
	 * @return
	 */
	public List<Ingredient> getIngredients(){
		List<Ingredient> ingredients = new LinkedList<>();
		for(IngredientQuantity ingredientQuantity : this.quantityIngredients){ ingredients.add(ingredientQuantity.getIngredient()); }
		return ingredients; 
	}

	@Override
	public int hashCode() {
		return Objects.hash(title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		return Objects.equals(title, other.title);
	}
	
	
	
	
}
