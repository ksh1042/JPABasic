# JPA Basic traning
- 김영한 - 자바 ORM 표준 JPA 프로그래밍

<hr/>

## 목표
- 기존에 사용하던 JDBC, SQL Mapper 패러다임에서 벗어나 ORM Framework 중 JPA 내부 동작 구조의 이해와 사용법 습득

<hr/>

### 몰랐던 부분의 핵심 메모
- 엔티티 매니저
> &nbsp;JPA는 사용자 요청시 엔티티 매니저 팩토리로부터 생성된 엔티티 매니저 단위로 트랜잭션을 생성하여 데이터를 관리한다.<br>
> 보통 사용자 요청수행이 완료되면 해당 엔티티 매니저는 그와 동시에 종료될때 자원을 반환해야 한다.
> ※ 엔티티 매니저는 Autocloseable 인터페이스를 지원하지 않으므로 try-with-resource 를 통한 예외 처리가 불가하며, 

- 엔티티 매니저 팩토리
> &nbsp;어플리케이션 단위로 엔티티 매니저를 생성하는 팩토리 클래스이며, 무언가의 비즈니스 로직에 의해 요청이 들어올 경우 엔티티 매니저를 생성하는 역할이다.<br>
> ※ 엔티티 매니저 팩토리는 어플리케이션 기동 중 생성되며 어플리케이션 종료 시 자원을 반환해야 한다.

- 지연쓰기
> &nbsp;각 동작이 호출될 때에 즉시 실행하지 않고 명령을 모아두었다 flush & commit 발생 시 명령을 한꺼번에 수행한다.<br>
> ※ persistence.xml 혹은 application.yml 등의 설정파일에서 hibernate.jdbc.batch_size 값을 변경하여 지연쓰기에 사용될 공간의 크기를 지정이 가능하다.

- 엔티티 영속성 라이프 사이클
> 기본적으로 JDBC, SQL Mapper 등을 통해 DB에 영속적으로 저장하는 라이프 사이클과 유사하다. 하지만 JPA 에선 객체지향적인 개발을 위해 영속성 컨텍스트 개념이 존재해 상이한 부분이 존재한다.

- 영속성 컨텍스트
> &nbsp;일종의 어플리케이션 단위로 데이터의 일관성을 보장해주는 1차 캐시와 비슷한 개념<br>
> J2EE 와 같은 엔터프라이즈 환경에서 엔티티 매니저와 영속성 컨텍스트는 N:1 관계의 형태를 띈다. 즉, 영속성 컨텍스트는 어플리케이션 단위이다.<br>
>> 비영속 : 영속적이지 않은 상태(어플리케이션 내에 객체를 처음 생성한 경우, transient 필드로 serializable 하지 않은 필드 등...)<br>
>> 영속(managed) : 영속성 컨텍스트에 관리되고 있는 상태(persist(), find()등으로 영속성 컨텍스트에서 관리되고 있는 상태)<br>
>> 준영속(detached, clear) : 영속성 컨텍스트에 저장되어있다 분리된 상태<br>
>> 삭제 : 삭제된 상태

- 영속성 컨텍스트 스냅샷
> JPA의 dirty checking을 가능하게 하는 부분으로, 영속성 컨텍스트 내에 스냅샷 정보를 기록하여 객체의 변경시 기존의 상태와 비교하여 변경을 감지 감지. 수정 등의 동작을 예약한다. 

- Flush
> 플러시 발생 시 변경을 감지하여 SQL을 생성 데이터베이스에 반영한다.(commit 상태는 아니므로 데이터베이스 정합성이 보장되지 않는 상태이다)<br>
> 엔티티 매니저를 직접 사용할 경우에는 직접 호출명시 하여 데이터베이스에 반영이 가능하나, 
> JPQL 쿼리 실행, 트랜잭션 커밋이 발생시엔 즉시 그 시점에서 자동으로 호출된다.<br>
> ※ 이름과 다르게 영속성 컨텍스의 내용을 비우지 않는다. 영속성 컨텍스트는 여전히 그대로 유지된다. 현재 데이터베이스의 상태에 지금까지의 동작을 반영하는 단계이며, 아직 확정까지 이르지는 않으므로 다른 트랜잭션에서는 현재 상태가 반영되어 있지 않은 상태이다.<br>
> ※ entityManager.setFlushMode(FlushModeType)을 통해 플리서 모드를 선택 가능하다.(AUTO-기본/COMMIT)

- 관계매핑
>- N:1 관계 : N 객체의 참조 변수에 @ManyToOne(), 1 객체의 참조 변수에 @OneToMany(mappedBy=N객체 참조변수의 변수이름)
>```java
>@Entity
>class Member {
> @Id
> @Column(name="member_id")
> private Long id;
> private String name;
> 
> @ManyToOne()
> private Team team;
> // Getter/Setter...
>} 
>@Entity
>class Team {
> @Id
> @Column(name="team_id")
> private Long id;
> private String name;
>
> @OneToMany(mappedBy="team") // Member.team
> List<Member> members = new ArrayList<>();
>
> // Getter/Setter...
>}
>```
- 연관관계 주인(Owner/mappedBy)
> 양방향 관계를 가질 경우 둘 중 어떤 엔티티가 FK의 주인이 될지 결정하는 것<br>
>> ※ 주인이 아닌 경우 mappedBy 사용하면 안됨!<br>
> 
> 외래키가 있는 곳을 주인으로 지정해야 한다.
> mappedBy가 선언된 시점에서 해당 레퍼런스는 읽기전용이 된다.

- ```@JoinColumn```
>- 외래키를 생성하고 관계를 정의하는 어노테이션으로 이해하면 편리하다.<br>
>- ```@JoinColumn(name = "_외래키가 참조할 컬럼 이름_")"```
>- 해당 어노테이션 인터페이스가 정의된 필드의 타입을 분석하여 해당 컬럼 이름을 추측하여 연결하는 것으로 예상된다.<br>
>- 해당 어노테이션 인터페이스를 정의하지 않고 연관 매핑을 진행하면 Join 테이블을 JPA가 임의로 생성하여 관리하게 된다. (```@JoinTable```어노테이션을 통해 Join 테이블에 대한 설정을 직접 수정 가능하다.)


- ```@OneToOne``` 관계에서의 프록시 한계점
>- 주테이블이 아닌 대상 테이블에 외래 키를 두어 1:1 관계 설정 시 LAZY 설정을 해주더라도 항상 즉시 로딩을 하게되어 성능상 이슈가 발생할 수 있다.
> 
- ```@Inheritance``` 객체의 상속 매핑
>- 객체의 상속 매핑을 위해 ```@Inheritance``` 어노테이션을 사용한다.
>- 매핑 전략으로 총 세가지 방법이 있다.
>>- InheritanceType.JOINED : 조인을 통해 상속구조를 표현하는 방식으로 가장 객체지향적으로 가깝다. 서브테이블에 PK&FK를 두어 슈퍼테이블과 관계를 가지는 방식이다.
>>- InheritanceType.SINGLE_TABLE : 한 테이블에 슈퍼클래스와 모든 서브클래스를 합치는 방식(해당 전략을 사용할 경우 ```@DiscriminatorColumn```전략이 기본적으로 설정되어있다).
>>- InheritanceType.TABLE_PER_CLASS : 각 서브클래스에 슈퍼클래스의 필드를 포함시키는 방식. 슈퍼클래스를 통해 조회 시 비효율적인 쿼리(모든 서브클래스의 ```UNION```)가 생성된다는 단점이 있다.
> 

- ```@DiscriminatorColumn```
>- 상속관계의 테이블의 슈퍼클래스 엔티티 클래스에 해당 어노테이션을 추가하면 ```DTYPE```컬럼(이름 변경가능)이 데이터베이스에 생성되며 해당 슈퍼클래스의 인스턴스가 어떤 서브클래스로 구성이 되었는지에 대해 기록이 된다.
>- 해당 어노테이션 인터페이스를 통해 컬럼을 넣으면 추후 그래프적으로 표현하기위해 해당
>- 서브클래스의 ```@DiscriminatorValue```를 통해 ```DTYPE```에 엔티티명이 아닌 사용자지정 이름입력이 가능하다.
> 

- ```@MappedSuperclass```
>- 매핑정보만 공통으로 사용하고 싶을때에 사용하는 어노테이션.
>- 상속받을 슈퍼클래스에 사용한다. 사용 방법은 아래와 같다.
>```java
>@MappedSuperclass
>public abstract class BaseEntity {
>   private String registUserId;
>
>   public String getRegistUserId() { return registUserId; }
>   public void setRegistUserId(String registUserId) { this.registUserId = registUserId; }
>}
>
>@Entity
>public abstract Board extends BaseEntity {
>   private BoardType type;
>   ...
>}
>```
>- 해딩 어노테이션을 사용한 클래스는 조회가 불가능하다.

- Proxy
>- 프록시는 JPA 표준 스펙이 아니며, 구현체에 따라 달라질 수 있다. 해당 내용은 Hibernate 기준으로 작성되었다.
> ```java
> public class Main {
>   public void main()
>   {
>     EntityManager em = ...;
>     
>     Board refBoard = em.getReference(Board.class, 1L);
> 
>     System.out.println("===Result===");
>     System.out.println("refBoard.getClass() = " + refBoard.getClass());
>   }
> }
> ```
> ```
> ====Result====
> Board$HibernateProxy$ocdVHpjy
> ```
>- ```entityManager.getReference()``` 호출 시 hibernate 가 사용자 클래스를 별도로 상속받아 Proxy 클래스를 생성한다.
>- 해당 프록시 클래스는 사용자 클래스와 동일하나 참조 데이터만 가지는 껍데기 클래스이다. 
>- 프록시 타입 비교시 ```==``` 비교 연산자가 아닌 ```instance of``` 비교 연산을 해야 한다. 대부분의 JPA Entity 객체들도 ```instance of ```를 통해 비교하는 것이 좋다.
>- 영속성 엔티티에 찾고자 하는 데이터가 존재한다면 프록시 엔티티가 아닌 일반적인 엔티티를 반환한다. 
>- 영속성 컨텍스트에 접근하지 못하는 경우에는 ```LazyInitializationException```가 발생되니 주의해야한다.(detach, clear, close 등을 통한 영속성 컨텍스트와의 연결이 끊긴 경우)
>- ```PersistenceUnitUtil.isLoaded(Object entity)```
>>- 아래의 방법을 통해 현재 프록시가 영속성 컨텍스트에 로드된 상태인지 확인할 수 있다.
>> ```java
>> EntityManagerFactory emf = ...;
>> EntityManager em = ...;
>> Entity entity = em.getReference(Entity.class, 1L);
>> emf.getPersistenceUnitUtil.isLoaded(entity);
>> ```
>- ```Hibernate.initialize(Object entity)```
>>- 아래의 방법을 통해 프록시를 강제 초기화 가능하다. (구현체인 Hibernate에서 제공되는 기능이며, 표준 JPA 스펙에는 존재하지 않는 기능) 
>> ```java
>> Entity entity = em.getReference(Entity.class, 1L);
>> Hibernate.initialize(entity);
>> ```

- ```FetchType.EAGER```/```FetchType.LAZY```
>>- FetchType.EAGER : 조회 시 즉시 로딩하는 전략.
>>- FetchType.LAZY : 조회 시 프록시를 준비하고 실제 데이터 접근 시 조회를 해오는 로딩 전략
>- EAGER 의 경우 예상치 못한 쿼리(다량의 Join)와 N+1 문제 등이 발생한다. 특별한 이유가 없다면 LAZY 로딩 전략을 사용하는 편이 좋다.
> 

- N+1 문제
>- FetchType.EAGER 로딩 전략을 사용할 때에 발생하는 문제로 A has B 관계에서 A를 조회 시 A List가 가지고 있을 모든 B의 데이터를 조회하기 위해 N개의 B 데이터를 조회해온다.<br><br>
> 즉, 최초에 발생하는 A 쿼리와 N개 만큼 발생하는 B 쿼리로 ```N+1``` 문제라 불린다.
<hr/>

## JPA 간단한 팁
- 객체 매핑
>- @Column(unique = true/false) : 제약조건 이름이 랜덤한 해시값으로 입력되어 잘 쓰이지 않으며 보통 @Table의 uniqueConstrains 옵션을 이용한다. 후자의 경우는 제약조건의 명칭까지 직접 설정이 가능하다.<br>
>- @Column(columnDefinition = "options") : 컬럼의 상세 조건 직접 입력이 가능하다.<br>
>```java
>...
>@Column(columnDefinition = "varchar2(100) default 'Kim'")
>private String name;
>...
>```
>```sql
>create table ...(
>   ...
>   name varchar2(100) default 'kim',
>   ...
>)
>``` 
>- @Column(percision = int) : BigDecimal, BigInteger와 같은 거대한 숫자를 다룰 때에 자릿수 제한을 위해 사용된다.
>- @Enumerated(EnumType.ORDINAL) : Enumerated의 기본 설정이지만, 열거형의 순서를 통해 값이 입력되므로 보통 안 쓰는 것을 권장한다.
>- @Lob : 멤버변수가 String 타입이면 자동으로 CLOB으로 생성되며, 그 외에는 전부 BLOB으로 생성된다.
- PK 시퀀스 매핑
>- GeneratedValue(strategy = GenerationType.TABLE) : 시퀀스 생성 전략을 테이블로 하는 방법으로, 별도의 키 관리 테이블을 생성 후 해당 테이블을 통해 다음 값을 가져오는 전략이다. 레이스 컨디션 등 성능상 여러 이슈가 있으므로 잘 쓰이지는 않으나, 모든 데이터베이스에서 사용이 가능하다는 특징이 있다.
 
- Primary Key 선택 팁
>PK의 성질 중 값이 변하면 안된다는 성질에 의해 자연 키를 PK 설정하는 것은 차후에 큰 문제를 야기할 수 있다. 그러므로 UUID, 시퀀스 등을 PK로 지정하여 사용하는 것이 옳다 한다.(by 김영한)

- GenerationValue.IDENTY 
>특이사항으로 해당 전략을 사용 중 PK설정을 하지 않을 경우 flush 혹은 commit이 아닌 persist를 호출 할 때에 insert query가 발생한다. insert query가 발생하기 전까지는 PK의 값을 알 수 없는 상황이기 때문이다.
>>※ 위의 경우와 같이 insert query가 발생한 경우 JDBC에서 해당 값을 확인할 수 있기 때문에 insert 직후 해당 데이터를 조회 시 select query가 발생하지 않는다.
- GenerationValue.SEQUENCE 
>persist가 호출 될 때에 다음 시퀀스 값을 호출하여 영속성 컨텍스트에 등록한다.<br>
>>※ allocationSize : 해당 설정 값을 통해 미리 시퀀스 값을 예측하여 사용, 레이스 컨디션 이슈 등의 성능 최적화를 도모한다. 하지만, 시퀀스로부터 생성시각 순서가 보장되지 않을 가능성이 높으며, 어플리케이션 종료시 메모리에 기록된 현재 시점의 시퀀스 값을 잃어버려 시퀀스 가용범위의 낭비가 발생할 수 있다. Table의 allocationSize도 동일한 동작을 수행한다.

- 양방향 관계 객체 매핑
>- 양방향 관계 객체 매핑 시 양 측 모두 서로를 매핑하는 코드를 작성하는 편이 좋다.<br>
>- 설계 시 단방향 매핑으로 끝내고 양방향이 필요한 경우 추후에 추가해도 늦지 않는다. 추후에 추가하는 방식이라도 테이블에는 전혀 영향이 없다.<br>
>- 불필요한 양방향 관계를 제거하여 단방향 관계를 유지할 경우 좀 더 객체지향적이며 비즈니스적 관심사가 명확하게 분리되는 코드를 작성할 수 있다. 

- N:M 관계 객체 매핑
> ```@ManyToMany``` 기본적으로 쓰지 않는 것을 권장한다. 보편적으로 N:M 관계를 표현 할 때에는 1:N + N:1 조합을 통해 중간 테이블을 엔티티로 승격시켜 N:M 구조를 표현한다.

<hr/>

