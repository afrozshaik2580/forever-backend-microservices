package com.forever.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forever.cartservice.entity.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	Cart findByUserId(Long userId);

}
