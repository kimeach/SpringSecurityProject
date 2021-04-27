package com.example.demo.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Model.User;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행 시킨다.
//로그인을 진행이 완료되면 시큐리티 Session을 만들어준다.(Security ContextHolder)
//오브젝트 => Authentication 타입 객체
//Authentication 안에 USer 정보가 있어야 한다.
//User Object 타입은 SUerDetails 타입 객체

//Security Session => Authentication => UserDetails 
public class PrincipalDetails  implements UserDetails{

	private User user; //콤포지션
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//해당 User의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { //계정이 잠겼는지
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //기간이 오래되었는지
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() { //계정이 활성화 되었는지
		// 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼계정 하기로 할 때
		//현재시간 - 로긴시간 => 1년 초과하면 return false;
		return true;
	}

	
}
