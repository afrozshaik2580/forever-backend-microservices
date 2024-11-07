package com.forever.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forever.authservice.entity.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
