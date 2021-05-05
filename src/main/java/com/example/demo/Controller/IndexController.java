package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(
			Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails userDetails) { //DI(������ ����)
		System.out.println("/test/login ============");
		
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); //���� �α��ν� error
		System.out.println("authentication : "+principalDetails.getUser());
		
		System.out.println("userDetails : "+userDetails.getUser());
		
		return "���� ���� Ȯ���ϱ�";
	}
	
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOauthLogin(
			Authentication authentication,
			@AuthenticationPrincipal OAuth2User oauth) { //DI(������ ����)
		System.out.println("/test/login ============");
		
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal(); //���� �α��ν� error
		System.out.println("authentication : "+oAuth2User.getAttributes());
		System.out.println("oauth : "+oauth.getAttributes());
		return "���� ���� Ȯ���ϱ�";
	}
	
	
	
	@GetMapping({"","/"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/user")
	public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principalDetails : "+principalDetails.getUser());
		return "user";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		repo.save(user);
		return "redirect:/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@Secured("ROLE_ADMIN") // Ư�� ���Ѹ� ����� �����ϰԲ� ����
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "��������";
	}
	
	@PreAuthorize ("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // data() �޼ҵ尡 ����Ǳ� ������ ����
	@PostAuthorize ("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // data() �޼ҵ尡 ����Ǳ� ������ ����
	
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "��������";
	}
}
