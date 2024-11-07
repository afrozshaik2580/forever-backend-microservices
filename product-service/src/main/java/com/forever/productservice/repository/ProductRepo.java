package com.forever.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forever.productservice.entity.Product;

import jakarta.transaction.Transactional;


@Repository
@Transactional
public interface ProductRepo extends JpaRepository<Product, Long>{

}
