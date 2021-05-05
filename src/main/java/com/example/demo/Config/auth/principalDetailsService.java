package com.example.demo.Config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;


//��ť��Ƽ �������� loginProcessingUrl("/login");
// /login ��û�� ���� �ڵ����� SUerDetailService Ÿ������ IOC�Ǿ��ִ� loadUserByUsername �ռ��� ����

@Service
public class principalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	//��ť��Ƽ Session(���� Authentication(���� UserDetails))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		//���⼭ �߿��� �� loginPage ���� form�ȿ� name="username"�� �ƴ� name="username2" �̷������� ���� ���� �� loadUserByUsername �Ű������� String username���� ����� ���� �ȹ޾�����.
		//���� loginPage ���� form�ȿ� name="username"�� �ƴ� name="username2" �̷������� ������ ���Ѵٸ� security ���� �κп� .loginPage() ���� �ؿ� .usernameParameter("���ϴ� ��") �̷������� �����ؾ��Ѵ�.
		System.out.println("username : "+username);
		User userEntity = repo.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		else if(userEntity == null){
			System.out.println("null �߳� �úη�");
		}
		return null;
	}

}
