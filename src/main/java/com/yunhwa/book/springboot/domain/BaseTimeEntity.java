package com.yunhwa.book.springboot.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The type Base time entity.
 * JPA Auditing 을 이용하기 위한 객체.
 * JPA Auditing? - JPA 사용시에 @CreatedDate, @LastModifiedDate 와 같이 자동으로 타임스탬프를 생성하는 등의 기능을 말한다.
 *  아래 객체처럼 createdDate, ModifiedDate 등은 여러객체에서 자주 사용하는 attribute로,
 *  이 형태와 같이 Base 도메인 객체로 분리해서 사용하는 것이 편하다.
 *
 * @MappedSuperClass - 사용시 객체를 다른 entity 객체가 상속 받았을 때,
 *   BaseTime entity에 있는 attribute들을 컬럼으로 인식하도록 해준다. (안넣으면 인식못함)
 *
 * @EntityListeners(AuditingLinstener.class) - BaseTimeEntity에 Auditing 기능을 포함시키는 역할을 한다.
 *
 * 위 두가지 외, 실제 JpaAuditing을 사용하고자 할 때는 Application 메인클래스에 @EnableJpaAuditing 를 추가해주어야만 적용된다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;
}
