package com.example.demo.Config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.demo.Model.User;

import lombok.Data;

//��ť��Ƽ�� /login �ּ� ��û�� ���� ����ä�� �α����� ���� ��Ų��.
//�α����� ������ �Ϸ�Ǹ� ��ť��Ƽ Session�� ������ش�.(Security ContextHolder)
//������Ʈ => Authentication Ÿ�� ��ü
//Authentication �ȿ� USer ������ �־�� �Ѵ�.
//User Object Ÿ���� SUerDetails Ÿ�� ��ü

//Security Session => Authentication => UserDetails 
@Data
public class PrincipalDetails  implements UserDetails,OAuth2User{

	private User user; //��������
	private Map<String,Object> attributes;
	
	//�Ϲ� �α���
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// oauth �α���
	public PrincipalDetails(User user,Map<String,Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	//�ش� User�� ������ �����ϴ� ��
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
	public boolean isAccountNonLocked() { //������ ������
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //�Ⱓ�� �����Ǿ�����
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() { //������ Ȱ��ȭ �Ǿ�����
		// �츮 ����Ʈ���� 1�⵿�� ȸ���� �α����� ���ϸ� �޸հ��� �ϱ�� �� ��
		//����ð� - �α�ð� => 1�� �ʰ��ϸ� return false;
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
