package com.example.demo.Config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;


//시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 SUerDetailService 타입으로 IOC되어있는 loadUserByUsername 합수가 실행

@Service
public class principalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	//시큐리티 Session(내부 Authentication(내부 UserDetails))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		//여기서 중요한 팁 loginPage 에서 form안에 name="username"이 아닌 name="username2" 이런식으로 설정 했을 때 loadUserByUsername 매개변수값 String username에서 제대로 값이 안받아진다.
		//만일 loginPage 에서 form안에 name="username"이 아닌 name="username2" 이런식으로 설정을 원한다면 security 설정 부분에 .loginPage() 설정 밑에 .usernameParameter("원하는 값") 이런식으로 설정해야한다.
		System.out.println("username : "+username);
		User userEntity = repo.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		else if(userEntity == null){
			System.out.println("null 뜨네 시부레");
		}
		return null;
	}

}
