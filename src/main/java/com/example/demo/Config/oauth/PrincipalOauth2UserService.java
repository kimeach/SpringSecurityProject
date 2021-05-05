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
	
	
	//구글로 부터 받은 UserRequest 데이터에 대한 후처리 되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest.getClientRegistration : "+userRequest.getClientRegistration());
		System.out.println("userRequest.getAccessToken() : "+userRequest.getAccessToken());
		//구글 로그인 버튼클릭 -> 구글 로그인 창 -> 로그인을 완료 -> code를 리턴(Oauth-client라이브러리) -> AccessToken 요청
		//UserRequest 정보 -> loadUser함수 호출 -> 구글로 부터 회원 프로필 받아준다.
	
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
			System.out.println("구글 로그인이 최초입니다.");
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
			System.out.println("구글 로그인이 최초가 아닙니다. 당신은 자동 회원가입이 되어 있습니다.");
		}
		return new PrincipalDetails(EntityUser, oauth2User.getAttributes());
	}
}
