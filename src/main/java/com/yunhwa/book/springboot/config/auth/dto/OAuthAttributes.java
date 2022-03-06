package com.yunhwa.book.springboot.config.auth.dto;

import java.util.Map;

import com.yunhwa.book.springboot.domain.user.Role;
import com.yunhwa.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
	}


	/**
	 * Of o auth attributes.
	 *
	 * @param regustrationId the regustration id
	 * @param userNameAttributeName the user name attribute name
	 * @param attributes the attributes
	 * @return the o auth attributes
	 *
	 * OAuth2User 에서 반환하는 사용자 정보는 Map 이기 때문에 값 하나하나를 변환해줘야만 한다.
	 */
	public static OAuthAttributes of(String regustrationId, String userNameAttributeName, Map<String, Object> attributes) {
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
				.name((String) attributes.get("name"))
				.email((String) attributes.get("email"))
				.picture((String) attributes.get("picture"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}

	/**
	 * To entity user.
	 * User 엔티티를 생성합니다.
	 * OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때입니다.
	 * 가입할 때의 기본 권한을 GUEST로 주기 위해서 role 빌더값에는 Role.GUEST를 사용합니다.
	 * OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser클래스를 생성합니다.
	 */
	public User toEntity() {
		return User.builder()
				.name(name)
				.email(email)
				.picture(picture)
				.role(Role.GUEST)
				.build();
	}

}
