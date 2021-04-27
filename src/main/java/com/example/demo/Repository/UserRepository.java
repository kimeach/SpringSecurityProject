package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
}
