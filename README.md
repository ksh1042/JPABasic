# JPA Basic traning
- 김영한 - 자바 ORM 표준 JPA 프로그래밍

## 목표
- 기존에 사용하던 JDBC, SQL Mapper 패러다임에서 벗어나 ORM Framework 중 JPA 내부 동작 구조의 이해와 사용법 습득

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