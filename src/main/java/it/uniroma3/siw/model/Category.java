package it.uniroma3.siw.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 
	
	@Column(nullable = false, unique = true)
	@NotBlank
	private String name; 
	
	@Column(nullable = false)
	@NotBlank
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Image picture;
	
	@OneToMany(mappedBy = "category",fetch = FetchType.EAGER,  cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,})
	private Set<Recipe> recipes;
	
	public Category() {
		this.recipes = new HashSet<>();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Recipe> getRicette() {
		return recipes;
	}

	public void setRicette(Set<Recipe> ricette) {
		this.recipes = ricette;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(name, other.name);
	}
	
	
	
}
