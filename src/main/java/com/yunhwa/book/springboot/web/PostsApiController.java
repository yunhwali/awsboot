package com.yunhwa.book.springboot.web;

import com.yunhwa.book.springboot.service.posts.PostsService;
import com.yunhwa.book.springboot.web.dto.PostsResponseDto;
import com.yunhwa.book.springboot.web.dto.PostsSaveRequestDto;
import com.yunhwa.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Posts api controller.
 * SPRING 에서 제공하는 다양한 방식의 DI(Constructor, Setter(method), Field )가 있지만,
 * 권장사항은 생성자를 통한 주입이다. final을 선언한 것은 그럴 일은 많지 않겠지만,
 * 빈주입이 한번 이상 일어나는 것을 방지하기 위함이다. (객체 주입 시 spring 내부에서 객체의 주소가 변경되는 것을 방지)
 * Lombok에서만 사용가능한 RequiredArgsConstructor를 사용하기 위함이기도 하다. (롬복 사용 시에는 이렇게 쓰는 것을 추천)
 * @RequiredArgsConstructor - 선언된 모든 final 필드가 포함된 생성자를 생성해준다.
 *
 * ** 여기서 @Autowired 사용을 추천하지 않는 이유 (Field Injection) - Autowired는 내부적으로 Reflection을 이용하여 DI를 수행함
 *  (참고로 java 17부터는 더 이상 reflection을 지원하지 않음)
 *   - Private 한 필드에 의존성을 주입할 수 있는 유일한 방법은 스프링 컨테이너가 클래스를 인스턴스 화하고 Reflection API를 사용하여 주입하는 것이다.
 *   - Spring DI에 의존적인 코드를 작성하게 됨 (순수 Java가 아님)
 *   - 의존성 주입 실패 시, Exception이 터지지 않고 참조가 연결되지 않기에 비지니스 로직시 문제가 발견될 수 있음
 *   - Bean을 찾지 못하거나, 여러 Bean이 존재하고 Primary, Qualifier, Order가 설정되지 않았을 때.
 *
 *  출처 참고 : https://lob-dev.tistory.com/entry/Spring-DI-Pattern-생성자-주입은-Reflection을-사용하는가
 *
 */
@RequiredArgsConstructor
@RestController
public class PostsApiController {

	private final PostsService postsService;

	@PostMapping("/api/v1/posts")
	public Long save(@RequestBody PostsSaveRequestDto requestDto) {
		return postsService.save(requestDto);
	}

	@PutMapping("/api/v1/posts/{id}")
	public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
		return postsService.update(id, requestDto);
	}

	@GetMapping("/api/v1/posts/{id}")
	public PostsResponseDto findById(@PathVariable Long id) {
		return postsService.findById(id);
	}

	@DeleteMapping("/api/v1/posts/{id}")
	public Long delete(@PathVariable Long id) {
		postsService.delete(id);
		return id;
	}

}
