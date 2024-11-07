package com.forever.productservice.entity;


import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Name of the product cannot be empty")
	private String name;
	
	@NotBlank(message = "Description should not be blank")
	@Size(min = 5,  message = "Description should be more than 5 characters")
	private String description;
	
	@DecimalMin(value = "1.0", message = "Price should be minimum $1.0")
	private double price;
	
	@NotBlank(message = "Category should not be blank")
	private String category;
	
	private String subCategory;
	private boolean bestSeller;
	
	@ElementCollection
	private List<String> sizes;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image", columnDefinition = "TEXT")
    private List<String> images;

}
