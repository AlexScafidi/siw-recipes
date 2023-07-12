//package it.uniroma3.siw.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//
//@Entity
//public class Address {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id; 
//	@Column(nullable=false)
//	@NotBlank
//	private String street;
//	@Column(nullable=false)
//	@NotBlank
//	private String city; 
//	@Column(nullable=false)
//	@NotBlank
//	private String nation; 
//	@Column(nullable=false)
//	@Min(value=0)
//	private int cap;
//	
//	public Address() {}
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getStreet() {
//		return street;
//	}
//	public void setStreet(String street) {
//		this.street = street;
//	}
//	public String getCity() {
//		return city;
//	}
//	public void setCity(String city) {
//		this.city = city;
//	}
//	public String getNation() {
//		return nation;
//	}
//	public void setNation(String nation) {
//		this.nation = nation;
//	}
//	public int getCap() {
//		return cap;
//	}
//	public void setCap(int cap) {
//		this.cap = cap;
//	} 
//	
//	
//
//}
//