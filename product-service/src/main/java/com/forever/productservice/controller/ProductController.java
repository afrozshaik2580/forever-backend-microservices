package com.forever.productservice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.productservice.dto.TokenDetails;
import com.forever.productservice.entity.Product;
import com.forever.productservice.service.ProductService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	private final ObjectMapper objectMapper=new ObjectMapper();
	
	@GetMapping("info")
	public TokenDetails isvalid(@RequestHeader("X-User-Details") String details) throws JsonMappingException, JsonProcessingException {
		return objectMapper.readValue(details, TokenDetails.class);
	}
	
	@GetMapping("")
	public ResponseEntity<List<Product>> getProducts(){
		return productService.getAllProducts();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> getProductDetails(@PathVariable Long id) {
		return productService.getProductDetails(id);
	} 
	
	@PostMapping("")
	public ResponseEntity<String> addProduct(@RequestBody @Valid Product product, BindingResult result, @RequestHeader("X-User-Details") String details) throws JsonMappingException, JsonProcessingException{
		
		TokenDetails tokenDetails=objectMapper.readValue(details, TokenDetails.class);
		if(!tokenDetails.getRoles().contains("ADMIN")) {
			return new ResponseEntity<String>("access denied", HttpStatus.UNAUTHORIZED);
		}
		if (result.hasErrors()) {
			String errorMessage=result.getAllErrors().get(0).getDefaultMessage();
			return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		try {
			return productService.addProduct(product);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return new ResponseEntity<String>("failed",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Product> putMethodName(@PathVariable Long id, @RequestBody Product product, @RequestHeader("X-User-Details") String details) throws JsonMappingException, JsonProcessingException{
		TokenDetails tokenDetails=objectMapper.readValue(details, TokenDetails.class);
		if(!tokenDetails.getRoles().contains("ADMIN")) {
			return new ResponseEntity<Product>(new Product(), HttpStatus.UNAUTHORIZED);
		}
		return productService.updateProduct(id, product);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> geleteProduct(@PathVariable Long id, @RequestHeader("X-User-Details") String details) throws JsonMappingException, JsonProcessingException{
		TokenDetails tokenDetails=objectMapper.readValue(details, TokenDetails.class);
		if(!tokenDetails.getRoles().contains("ADMIN")) {
			return new ResponseEntity<String>("Unauthorizes", HttpStatus.UNAUTHORIZED);
		}
		return productService.deleteProduct(id);
	}
}
