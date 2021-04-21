package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Service.securityService;

@Controller
@RequestMapping("/secu")
public class securityController {

	@Autowired
	securityService serv;
	
	@GetMapping
	public ModelAndView getIndex() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("list", serv.getAll());
		return mav;
	}
	
	
}
