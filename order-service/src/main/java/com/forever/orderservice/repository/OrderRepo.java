package com.forever.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forever.orderservice.entity.Order;
import java.util.List;


@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{

	List<Order> findByUserId(Long userId);
}
