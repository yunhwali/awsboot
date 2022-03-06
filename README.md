# MEMO

#### Spring DI
SPRING 에서 제공하는 다양한 방식의 DI(Constructor, Setter(method), Field )가 있지만, 권장사항은 생성자를 통한 주입이다. 
- final을 선언한 것은 그럴 일은 많지 않겠지만, 빈주입이 한번 이상 일어나는 것을 방지하기 위함이다. (객체 주입 시 spring 내부에서 객체의 주소가 변경되는 것을 방지)
- Lombok에서만 사용가능한 RequiredArgsConstructor를 사용하기 위함이기도 하다. (롬복 사용 시에는 이렇게 쓰는 것을 추천)
- Lombok @RequiredArgsConstructor - 선언된 모든 final 필드가 포함된 생성자를 생성해준다.

여기서 @Autowired 사용을 추천하지 않는 이유 (Field Injection) - Autowired는 내부적으로 Reflection을 이용하여 DI를 수행함
(참고로 java 17부터는 더 이상 reflection을 지원하지 않음)
   - Private 한 필드에 의존성을 주입할 수 있는 유일한 방법은 스프링 컨테이너가 클래스를 인스턴스 화하고 Reflection API를 사용하여 주입하는 것이다.
   - Spring DI에 의존적인 코드를 작성하게 됨 (순수 Java가 아님)
   - 의존성 주입 실패 시, Exception이 터지지 않고 참조가 연결되지 않기에 비지니스 로직시 문제가 발견될 수 있음
   - Bean을 찾지 못하거나, 여러 Bean이 존재하고 Primary, Qualifier, Order가 설정되지 않았을 때.

 출처 참고 : https://lob-dev.tistory.com/entry/Spring-DI-Pattern-생성자-주입은-Reflection을-사용하는가
 
 
 
#### JPA

##### Update.
@Transactional 이 선언된 메서드에서 Entity 객체 내에 존재하는 메서드를 통해 객체 상태 값이 변경될 경우, 
JPA에서는 별도로 repository의 save를 호출하지 않고도 영속성 컨텍스트에 의해 상태값 업데이트가 가능하기 때문에 아래와 같이 사용가능하다.
```
Post.java

public void update (String title, String content) {
	this.title = title;
	this.content = content;
}
```
InnoDB의 InsertBuffer 개념과 비슷한데, 트랜젝션 안에서 데이터를 가져온 후 이 데이터 값을 변경하면 트랜젝션이
끝나는 시점에 해당 테이블에 변경분을 반영함. 이 개념을 더티체킹(Dirty checking)이라고 함.


##### JPA Auditing 
> JPA Auditing? 
> JPA 사용시에 @CreatedDate, @LastModifiedDate 와 같이 자동으로 타임스탬프를 생성하는 등의 기능을 말한다.
> 아래 객체처럼 createdDate, ModifiedDate 등은 여러객체에서 자주 사용하는 attribute로,
> 이 형태와 같이 Base 도메인 객체로 분리해서 사용하는 것이 편하다.
```
BaseTimeEntity.java

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;
}
```
- @MappedSuperClass - 사용시 객체를 다른 entity 객체가 상속 받았을 때, BaseTime entity에 있는 attribute들을 컬럼으로 인식하도록 해준다. (안넣으면 인식못함)
- @EntityListeners(AuditingLinstener.class) - BaseTimeEntity에 Auditing 기능을 포함시키는 역할을 한다.
위 두가지 외, 실제 JpaAuditing을 사용하고자 할 때는 Application 메인클래스에 @EnableJpaAuditing 를 추가해주어야만 적용된다.

##### Annotation
> @Enumerated(EnumType.STRING)
* JPA로 데이터베이스에 저장할 때 Enum값을 어떤 형태로 저장할지를 결정함.
* 기본적으로는 int로 된 숫자가 저장됨
* 숫자로 저장되면 데이터베이스로 확인할때 알기가 힘듦
* 그래서 문자열 (EnumType.STRING) 으로 저장될 수 있도록 선언.