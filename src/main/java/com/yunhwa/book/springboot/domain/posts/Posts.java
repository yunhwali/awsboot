package com.yunhwa.book.springboot.domain.posts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yunhwa.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500, nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	private String author;

	@Builder
	public Posts(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

	/**
	 * Update.
	 * @Transactional 이 선언된 메서드에서 Entity 객체 내에 존재하는 메서드를 통해
	 * 객체 상태 값이 변경될 경우, JPA에서는 별도로 repository의 save를 호출하지 않고도 영속성 컨텍스트에 의해
	 * 상태값 업데이트가 가능하기 때문에 아래와 같이 사용가능하다.
	 * InnoDB의 InsertBuffer 개념과 비슷한데, 트랜젝션 안에서 데이터를 가져온 후 이 데이터 값을 변경하면 트랜젝션이
	 * 끝나는 시점에 해당 테이블에 변경분을 반영함. 이 개념을 더티체킹(Dirty checking)이라고 함.
	 */
	public void update (String title, String content) {
		this.title = title;
		this.content = content;
	}
}
