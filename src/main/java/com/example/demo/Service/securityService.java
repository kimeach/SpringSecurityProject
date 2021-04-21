package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
import com.example.demo.Repository.securityRepository;

@Service
public class securityService {

	@Autowired
	private securityRepository repo;
	
	public List<User> getAll(){
		return repo.findAll();
	}
	
}
