package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.User;

@Repository
public interface securityRepository extends JpaRepository<User, Object>{

}
