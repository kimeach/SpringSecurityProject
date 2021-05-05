package com.example.demo.Config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	
	//���۷� ���� ���� UserRequest �����Ϳ� ���� ��ó�� �Ǵ� �Լ�
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest.getClientRegistration : "+userRequest.getClientRegistration());
		System.out.println("userRequest.getAccessToken() : "+userRequest.getAccessToken());
		//���� �α��� ��ưŬ�� -> ���� �α��� â -> �α����� �Ϸ� -> code�� ����(Oauth-client���̺귯��) -> AccessToken ��û
		//UserRequest ���� -> loadUser�Լ� ȣ�� -> ���۷� ���� ȸ�� ������ �޾��ش�.
	
		OAuth2User oauth2User = super.loadUser(userRequest);
		System.out.println("super.loadUser(userRequest).getAttributes() : "+oauth2User.getAttributes());
		
		String provider = userRequest.getClientRegistration().getClientId(); // google
		String providerID = oauth2User.getAttribute("sub");
		String username = provider+"_"+providerID;
		String password = bCryptPasswordEncoder.encode("admin");
		String email = oauth2User.getAttribute("email");
		String role = "ROLE_USER";
		
		User EntityUser = userRepository.findByUsername(username);
		
		if(EntityUser == null) {
			System.out.println("���� �α����� �����Դϴ�.");
			EntityUser = User.builder()
			.username(username)
			.provider(providerID)
			.providerId(providerID)
			.password(password)
			.email(email)
			.role(role)
			.build();
			
			userRepository.save(EntityUser);
		}
		else {
			System.out.println("���� �α����� ���ʰ� �ƴմϴ�. ����� �ڵ� ȸ�������� �Ǿ� �ֽ��ϴ�.");
		}
		return new PrincipalDetails(EntityUser, oauth2User.getAttributes());
	}
}
