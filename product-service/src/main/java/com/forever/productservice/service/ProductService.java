package com.forever.productservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.forever.productservice.entity.Product;
import com.forever.productservice.repository.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<List<Product>>(productRepo.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<String> addProduct(Product product) throws IOException {
		Product savedProduct = productRepo.save(product);
		return new ResponseEntity<String>("product added with id " + savedProduct.getId(), HttpStatus.CREATED);
	}

	public ResponseEntity<Product> getProductDetails(Long id) {
		Product product = productRepo.findById(id).orElse(null);
		if (product == null) {
			return new ResponseEntity<Product>(product, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	public ResponseEntity<Product> updateProduct(Long id, Product product) {

		Product exixtingProduct = productRepo.findById(id).orElse(null);
		if (exixtingProduct == null) {
			return new ResponseEntity<Product>(exixtingProduct, HttpStatus.NOT_FOUND);
		}
		product.setId(id);
		Product updatedProduct = productRepo.save(product);
		return new ResponseEntity<Product>(updatedProduct, HttpStatus.CREATED);
	}

	public ResponseEntity<String> deleteProduct(Long id) {
		Product product = productRepo.findById(id).orElse(null);
		if (product == null) {
			return new ResponseEntity<String>("Product not found", HttpStatus.NOT_FOUND);
		}
		productRepo.delete(product);
		return new ResponseEntity<String>("Product deleted successfully", HttpStatus.OK);
	}

}
