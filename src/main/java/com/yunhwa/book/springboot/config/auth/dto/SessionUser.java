package com.yunhwa.book.springboot.config.auth.dto;

import java.io.Serializable;

import com.yunhwa.book.springboot.domain.user.User;
import lombok.Getter;

/**
 * The type Session user.
 * User 정보를 직렬화 하기위한 객체.
 * User 클래스는 entity 이므로 다른 객체와의 관계 형성에 영향을 줄 수 있기 때문에,
 * 분리하여 사용하는 것이 좋다.
 */
@Getter
public class SessionUser implements Serializable {
	private String name;
	private String email;
	private String picture;

	public SessionUser(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.picture = user.getPicture();
	}
}
