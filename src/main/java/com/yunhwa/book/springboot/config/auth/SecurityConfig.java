package com.yunhwa.book.springboot.config.auth;

import com.yunhwa.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().frameOptions().disable() //h2 console 화면을 사용하기 위해 해당 옵션들을 disable합니다.
				.and().authorizeRequests() //URL별 권한관리를 설정하는 옵션의 시작 -
										   // authorizeRequsets가 선언되어야만 AntMatchers 옵션 사용이 가능
				.antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
				.antMatchers("/api/v1/**").hasRole(Role.USER.name())
				.anyRequest().authenticated()
				.and().logout().logoutSuccessUrl("/")
				.and().oauth2Login()
				.userInfoEndpoint() // oauth2로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당함.
				.userService(customOAuth2UserService); //소셜 로그인 성공 시 후속 조치를 진행할 UserService인터페이스의 구현체를 등록.
													   //리소스 서버(소셜서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 할 수 있음.
	}
}
