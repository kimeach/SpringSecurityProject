package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.User;
import com.example.demo.Service.securityService;

@RestController
@RequestMapping("/rest")
public class securityRestController {

	@Autowired
	securityService serv;
	
	@PostMapping("/allUser")
	public ResponseEntity<List<User>> getUserAll(){
		return new ResponseEntity<>(serv.getAll(),HttpStatus.OK);
	}
}
