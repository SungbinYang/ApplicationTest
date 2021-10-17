## JUnit5 시작하기
- 스프링 부트 프로젝트 만들기 
  * 2.2+ 버전의 스프링 부트 프로젝트를 만든다면 기본으로 JUnit 5 의존성 추가 됨.
- 스프링 부트 프로젝트 사용하지 않는다면?
``` xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.5.2</version>
    <scope>test</scope>
</dependency>
```
- 기본 애노테이션
  * @Test (Junit4: @Test)
  * @BeforeAll (Junit4: BeforeClass) : 테스트 시작 전 딱 1번만 실행
  * @AfterAll (Junit4: AfterClass) : 테스트 시작 전 딱 1번만 실행
  * @BeforeEach (Junit4: Before) : 각 테스트 시작 전
  * @AfterEach (Junit4: After) : 각 테스트 시작 후
  * @Disabled (Junit4: @Ignored) : 테스트 코드 실행 안하게 해줌

## JUnit5 테스트 이름 표시하기
- @DisplayNameGeneration
  * Method와 Class 레퍼런스를 사용해서 테스트 이름을 표기하는 방법 설정.
  * 기본 구현체로 ReplaceUnderscores 제공
- @DisplayName
  * 어떤 테스트인지 테스트 이름을 보다 쉽게 표현할 수 있는 방법을 제공하는 애노테이션.
  * @DisplayNameGeneration 보다 우선 순위가 높다.
- 참고
  * https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-names

## JUnit5 Assertions
- org.junit.jupiter.api.Assertions.*

|||
|------|---|
|실제 값이 기대한 값과 같은지 확인|assertEqulas(expected, actual)|
|값이 null이 아닌지 확인|assertNotNull(actual)|
|다음 조건이 참(true)인지 확인|assertTrue(boolean)|
|모든 확인 구문 확인|assertAll(executables...)|
|예외 발생 확인|assertThrows(expectedType, executable)|
|특정 시간 안에 실행이 완료되는지 확인|assertTimeout(duration, executable)|

- 마지막 매개변수로 Supplier<String> 타입의 인스턴스를 람다 형태로 제공할 수 있다.
  * 복잡한 메시지 생성해야 하는 경우 사용하면 실패한 경우에만 해당 메시지를 만들게 할 수 있다.
- [AssertJ](https://joel-costigliola.github.io/assertj/), [Hemcrest](https://hamcrest.org/JavaHamcrest/), [Truth](https://truth.dev/) 등의 라이브러리를 사용할 수도 있다.

## JUnit 5: 조건에 따라 테스트 실행하기
- 특정한 조건을 만족하는 경우에 테스트를 실행하는 방법.
- org.junit.jupiter.api.Assumptions.*
  * assumeTrue(조건)
  * assumingThat(조건, 테스트)
- @Enabled___ 와 @Disabled___
  * OnOS
  * OnJre
  * IfSystemProperty
  * IfEnvironmentVariable
  * If (Depercated)

## JUnit 5: 태깅과 필터링
- 테스트 그룹을 만들고 원하는 테스트 그룹만 테스트를 실행할 수 있는 기능.
- @Tag
  * 테스트 메소드에 태그를 추가할 수 있다.
  * 하나의 테스트 메소드에 여러 태그를 사용할 수 있다.
- 인텔리J에서 특정 태그로 테스트 필터링 하는 방법

![스크린샷 2021-09-29 오전 6 11 26](https://user-images.githubusercontent.com/18282470/135166304-f55432e2-a28b-495c-99aa-170a5b78dd07.png)

- 메이븐에서 테스트 필터링 하는 방법

``` xml
<plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <groups>fast | slow</groups>
    </configuration>
</plugin>
```
- 참고
  * https://maven.apache.org/guides/introduction/introduction-to-profiles.html
  * https://junit.org/junit5/docs/current/user-guide/#running-tests-tag-expressions

## JUnit5 커스텀 태그
- JUnit 5 애노테이션을 조합하여 커스텀 태그를 만들 수 있다.

``` java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag("fast")
@Test
public @interface FastTest {
}
```

``` java
@FastTest
@DisplayName("스터디 만들기 fast")
void create_new_study() {

@SlowTest
@DisplayName("스터디 만들기 slow")
void create_new_study_again() 
```

## JUnit 5: 테스트 반복하기
- @RepeatedTest
  * 반복 횟수와 반복 테스트 이름을 설정할 수 있다.
    * {displayName}
    * {currentRepetition}
    * {totalRepetitions}
  * RepetitionInfo 타입의 인자를 받을 수 있다.
- ParameterizedTest
  * 테스트에 여러 다른 매개변수를 대입해가며 반복 실행한다.
    * {displayName}
    * {index}
    * {arguments}
    * {0}, {1}, ..
- 인자 값들의 소스
  * @ValueSource
  * @NullSource, @EmptySource, @NullAndEmptySource
  * @EnumSource
  * @MethodSource
  * @CsvSource
  * @CvsFileSource
  * @ArgumentSource
- 인자 값 타입 변환
  * 암묵적인 타입 변환
    * [레퍼런스](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-aggregation) 참고
  * 명시적인 타입 변환
    * SimpleArgumentConverter 상속 받은 구현체 제공
    * @ConvertWith
- 인자 값 조합
  * ArgumentsAccessor
  * 커스텀 Accessor
    * ArgumentsAggregator 인터페이스 구현
    * @AggregateWith
- 참고
  * https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests

## JUnit 5: 테스트 인스턴스
- JUnit은 테스트 메소드 마다 테스트 인스턴스를 새로 만든다.
  * 이것이 기본 전략.
  * 테스트 메소드를 독립적으로 실행하여 예상치 못한 부작용을 방지하기 위함이다.
  * 이 전략을 JUnit 5에서 변경할 수 있다.
- @TestInstance(Lifecycle.PER_CLASS)
  * 테스트 클래스당 인스턴스를 하나만 만들어 사용한다.
  * 경우에 따라, 테스트 간에 공유하는 모든 상태를 @BeforeEach 또는 @AfterEach에서 초기화 할 필요가 있다.
  * @BeforeAll과 @AfterAll을 인스턴스 메소드 또는 인터페이스에 정의한 default 메소드로 정의할 수도 있다.

## JUnit 5: 테스트 순서
- 실행할 테스트 메소드 특정한 순서에 의해 실행되지만 어떻게 그 순서를 정하는지는 의도적으로 분명히 하지 않는다. (테스트 인스턴스를 테스트 마다 새로 만드는 것과 같은 이유)
- 경우에 따라, 특정 순서대로 테스트를 실행하고 싶을 때도 있다. 그 경우에는 테스트 메소드를 원하는 순서에 따라 실행하도록 @TestInstance(Lifecycle.PER_CLASS)와 함께 @TestMethodOrder를 사용할 수 있다.
  * MethodOrderer 구현체를 설정한다.
  * 기본 구현체
    * Alphanumeric
    * OrderAnnoation
    * Random

## JUnit 5: junit-platform.properties
- JUnit 설정 파일로, 클래스패스 루트 (src/test/resources/)에 넣어두면 적용된다.
- 테스트 인스턴스 라이프사이클 설정
 
``` properties
junit.jupiter.testinstance.lifecycle.default = per_class
```

- 확장팩 자동 감지 기능

``` properties
junit.jupiter.extensions.autodetection.enabled = true
```

- @Disabled 무시하고 실행하기

``` properties
junit.jupiter.conditions.deactivate = org.junit.*DisabledCondition
```

- 테스트 이름 표기 전략 설정

``` properties
junit.jupiter.displayname.generator.default = \
    org.junit.jupiter.api.DisplayNameGenerator$ReplaceUnderscores
```

## JUnit 5: 확장 모델
- JUnit 4의 확장 모델은 @RunWith(Runner), TestRule, MethodRule.
- JUnit 5의 확장 모델은 단 하나, Extension.

- 확장팩 등록 방법
  * 선언적인 등록 @ExtendWith
  * 프로그래밍 등록 @RegisterExtension
  * 자동 등록 자바 ServiceLoader 이용
- 확장팩 만드는 방법
  * 테스트 실행 조건
  * 테스트 인스턴스 팩토리
  * 테스트 인스턴스 후-처리기
  * 테스트 매개변수 리졸버
  * 테스트 라이프사이클 콜백
  * 예외 처리
- 참조
  * https://junit.org/junit5/docs/current/user-guide/#extensions

## JUnit 5: JUnit 4 마이그레이션
- junit-vintage-engine을 의존성으로 추가하면, JUnit 5의 junit-platform으로 JUnit 3과 4로 작성된 테스트를 실행할 수 있다.
  * @Rule은 기본적으로 지원하지 않지만, junit-jupiter-migrationsupport 모듈이 제공하는 @EnableRuleMigrationSupport를 사용하면 다음 타입의 Rule을 지원한다.
    * ExternalResource
    * Verifier
    * ExpectedException

|JUnit 4|JUnit 5|
|------|---|
|@Category(Class)|@Tag(String)|
|@RunWith, @Rule, @ClassRule|@ExtendWith, @RegisterExtension|
|@Ignore|@Disabled|
|@Before, @After, @BeforeClass, @AfterClass|@BeforeEach, @AfterEach, @BeforeAll, @AfterAll|

## Mockito 소개
- Mock: 진짜 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체.
- [Mockito](https://site.mockito.org/): Mock 객체를 쉽게 만들고 관리하고 검증할 수 있는 방법을 제공한다.
- 테스트를 작성하는 자바 개발자 50%+ 사용하는 Mock 프레임워크.
  * https://www.jetbrains.com/lp/devecosystem-2021/java/
- 현재 최신 버전 3.12.4
- 단위 테스트에 고찰
  * https://martinfowler.com/bliki/UnitTest.html

![스크린샷 2021-10-05 오전 5 37 21](https://user-images.githubusercontent.com/18282470/135921382-1582b938-9cd2-4d6b-90ff-8ee1fb7be22b.png)

- 대체제: [EasyMock](https://easymock.org/), [JMock](http://jmock.org/)

## Mockito 시작하기
- 스프링 부트 2.2+ 프로젝트 생성시 spring-boot-starter-test에서 자동으로 Mockito 추가해 줌.

- 스프링 부트 쓰지 않는다면, 의존성 직접 추가.
``` xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>3.1.0</version>
    <scope>test</scope>
</dependency>


<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>3.1.0</version>
    <scope>test</scope>
</dependency>
```

- 다음 세 가지만 알면 Mock을 활용한 테스트를 쉽게 작성할 수 있다.
  * Mock을 만드는 방법
  * Mock이 어떻게 동작해야 하는지 관리하는 방법
  * Mock의 행동을 검증하는 방법
- Mockito 레퍼런스
  * https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html

## Mock 객체 만들기
- Mockito.mock() 메소드로 만드는 방법
``` java
   MemberService memberService = mock(MemberService.class);
   StudyRepository studyRepository = mock(StudyRepository.class);
```

- @Mock 애노테이션으로 만드는 방법
  * JUnit 5 extension으로 MockitoExtension을 사용해야 한다.
  * 필드
  * 메소드 매개변수
``` java
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock MemberService memberService;

    @Mock StudyRepository studyRepository;
}
```
``` java
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    
    @Test
    void createStudyService(@Mock MemberService memberService,
                            @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

}
```

## Mock 객체 Stubbing
- 모든 Mock 객체의 행동
  * Null을 리턴한다. (Optional 타입은 Optional.empty 리턴)
  * Primitive 타입은 기본 Primitive 값.
  * 콜렉션은 비어있는 콜렉션.
  * Void 메소드는 예외를 던지지 않고 아무런 일도 발생하지 않는다.
- Mock 객체를 조작해서
  * 특정한 매개변수를 받은 경우 특정한 값을 리턴하거나 예뢰를 던지도록 만들 수 있다.
    * [How about some stubbing?](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#2)
    * [Argument matchers](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#3)
  * Void 메소드 특정 매개변수를 받거나 호출된 경우 예외를 발생 시킬 수 있다.
    * [Subbing void methods with exceptions](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#5)
  * 메소드가 동일한 매개변수로 여러번 호출될 때 각기 다르게 행동호도록 조작할 수도 있다.
    * [Stubbing consecutive calls](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#10)

## Mock 객체 확인
- Mock 객체가 어떻게 사용이 됐는지 확인할 수 있다.
  * 특정 메소드가 특정 매개변수로 몇번 호출 되었는지, 최소 한번은 호출 됐는지, 전혀 호출되지 않았는지
    * [Verifying exact number of invocations](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#exact_verification)
  * 어떤 순서대로 호출했는지
    * [Verification in order](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#in_order_verification)
  * 특정 시간 이내에 호출됐는지
    * [Verification with timeout](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#verification_timeout)
  * 특정 시점 이후에 아무 일도 벌어지지 않았는지
    * [Finding redundant invocations](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#finding_redundant_invocations)

## Mockito BDD 스타일 API
- [BDD](https://en.wikipedia.org/wiki/Behavior-driven_development): 애플리케이션이 어떻게 “행동”해야 하는지에 대한 공통된 이해를 구성하는 방법으로, TDD에서 창안했다.
- 행동에 대한 스팩
  * Title
  * Narrative
    * As a  / I want / so that
  * Acceptance criteria
    * Given / When / Then
- Mockito는 BddMockito라는 클래스를 통해 BDD 스타일의 API를 제공한다.
- When -> Given
``` java
given(memberService.findById(1L)).willReturn(Optional.of(member));
given(studyRepository.save(study)).willReturn(study);
```
- Verify -> Then
``` java
then(memberService).should(times(1)).notify(study);
then(memberService).shouldHaveNoMoreInteractions();
```
- 참고
  * https://javadoc.io/static/org.mockito/mockito-core/3.2.0/org/mockito/BDDMockito.html
  * https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#BDD_behavior_verification

## Testcontainers 소개
- 테스트에서 도커 컨테이너를 실행할 수 있는 라이브러리.
  * https://www.testcontainers.org/
  * 테스트 실행시 DB를 설정하거나 별도의 프로그램 또는 스크립트를 실행할 필요 없다.
  * 보다 Production에 가까운 테스트를 만들 수 있다.
  * 테스트가 느려진다.

## Testcontainers 설치
- Testcontainers JUnit 5 지원 모듈 설치

``` xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>1.15.1</version>
    <scope>test</scope>
</dependency>
```

- https://www.testcontainers.org/test_framework_integration/junit_5/
- @Testcontainers
  * JUnit 5 확장팩으로 테스트 클래스에 @Container를 사용한 필드를 찾아서 컨테이너 라이프사이클 관련 메소드를 실행해준다.
- @Container
  * 인스턴스 필드에 사용하면 모든 테스트 마다 컨테이너를 재시작 하고, 스태틱 필드에 사용하면 클래스 내부 모든 테스트에서 동일한 컨테이너를 재사용한다.
- 여러 모듈을 제공하는데, 각 모듈은 별도로 설치해야 한다.
  * Postgresql 모듈 설치
  * https://www.testcontainers.org/modules/databases/
  * https://www.testcontainers.org/modules/databases/postgres/

``` xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <version>1.15.1</version>
    <scope>test</scope>
</dependency>
```

- application.properties

``` properties
spring.datasource.url=jdbc:tc:postgresql:///studytest
spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver
```

## Testcontainers 기능 살펴보기
- 컨테이너 만들기
  * New GenericContainer(String imageName)
- 네트워크
  * withExposedPorts(int...)
  * getMappedPort(int)
- 환경 변수 설정
  * withEnv(key, value)
- 명령어 실행
  * withCommand(String cmd...)
- 사용할 준비가 됐는지 확인하기
  * waitingFor(Wait)
  * Wait.forHttp(String url)
  * Wait.forLogMessage(String message)
- 로그 살펴보기
  * getLogs()
  * followOutput()

## Testcontainers, 컨테이너 정보를 스프링 테스트에서 참조하기
- @ContextConfiguration
  * 스프링이 제공하는 애노테이션으로, 스프링 테스트 컨텍스트가 사용할 설정 파일 또는 컨텍스트를 커스터마이징할 수 있는 방법을 제공한다.
- ApplicationContextInitializer
  * 스프링 ApplicationContext를 프로그래밍으로 초기화 할 때 사용할 수 있는 콜백 인터페이스로, 특정 프로파일을 활성화 하거나, 프로퍼티 소스를 추가하는 등의 작업을 할 수 있다.
- TestPropertyValues
  * 테스트용 프로퍼티 소스를 정의할 때 사용한다.
- Environment
  * 스프링 핵심 API로, 프로퍼티와 프로파일을 담당한다.
- 전체 흐름
  * Testcontainer를 사용해서 컨테이너 생성
  * ApplicationContextInitializer를 구현하여 생선된 컨테이너에서 정보를 축출하여 Environment에 넣어준다.
  * @ContextConfiguration을 사용해서 ApplicationContextInitializer 구현체를 등록한다.
  * 테스트 코드에서 Environment, @Value, @ConfigurationProperties 등 다양한 방법으로 해당 프로퍼티를 사용한다.

## Testcontainers 도커 Compose 사용하기
- 테스트에서 (서로 관련있는) 여러 컨테이너를 사용해야 한다면?
- Docker Compose: https://docs.docker.com/compose/
  * 여러 컨테이너를 한번에 띄우고 서로 간의 의존성 및 네트워크 등을 설정할 수 있는 방법
  * docker-compose up / down
- Testcontainser의 docker compose 모듈을 사용할 수 있다.
  * https://www.testcontainers.org/modules/docker_compose/
- 대체제: https://github.com/palantir/docker-compose-rule
  * 2019 가을 KSUG 발표 자료 참고
  * https://bit.ly/2q8S3Qo
- 도커 Compose 서비스 정보 참조하기
- 특정 서비스 Expose
``` java
@Container
static DockerComposeContainer composeContainer =
        new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
        .withExposedService("study-db", 5432);
```
- Compose 서비스 정보 참조
``` java
static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of("container.port=" + composeContainer.getServicePort("study-db", 5432))
                .applyTo(context.getEnvironment());
    }
}
```

## JMeter 소개
- https://jmeter.apache.org/
- 성능 측정 및 부하 (load) 테스트 기능을 제공하는 오픈 소스 자바 애플리케이션.
- 다양한 형태의 애플리케이션 테스트 지원
  * 웹 - HTTP, HTTPS
  * SOAP / REST 웹 서비스
  * FTP
  * 데이터베이스 (JDBC 사용)
  * Mail (SMTP, POP3, IMAP)
- CLI 지원
  * CI 또는 CD 툴과 연동할 때 편리함.
  * UI 사용하는 것보다 메모리 등 시스템 리소스를 적게 사용.
- 주요 개념
  * Thread Group: 한 쓰레드 당 유저 한명
  * Sampler: 어떤 유저가 해야 하는 액션
  * Listener: 응답을 받았을 할 일 (리포팅, 검증, 그래프 그리기 등)
  * Configuration: Sampler 또는 Listener가 사용할 설정 값 (쿠키, JDBC 커넥션 등)
  * Assertion: 응답이 성공적인지 확인하는 방법 (응답 코드, 본문 내용 등)
- 대체제:
  * [Gatling](https://gatling.io/)
  * [nGrinder](https://naver.github.io/ngrinder/)

## JMeter 설치
- https://jmeter.apache.org/download_jmeter.cgi
- 압축 파일 받고 압축 파일 풀기. 원한다면 PATH에 bin 디렉토리를 추가.
-  bin/jmeter 실행하기

<img width="1434" alt="스크린샷 2021-10-12 오전 5 16 00" src="https://user-images.githubusercontent.com/18282470/136850498-0c581b99-ee6a-4503-8059-28fc422e20a5.png">

## JMeter 사용하기
- Thread Group 만들기
  * Number of Threads: 쓰레드 개수
  * Ramp-up period: 쓰레드 개수를 만드는데 소요할 시간
  * Loop Count: infinite 체크 하면 위에서 정한 쓰레드 개수로 계속 요청 보내기. 값을 입력하면 해당 쓰레드 개수 X 루프 개수 만큼 요청 보냄.
- Sampler 만들기
  * 여러 종류의 샘플러가 있지만 그 중에 우리가 사용할 샘플러는 HTTP Request 샘플러.
  * HTTP Sampler
    * 요청을 보낼 호스트, 포트, URI, 요청 본문 등을 설정
  * 여러 샘플러를 순차적으로 등록하는 것도 가능하다.
- Listener 만들기
  * View Results Tree
  * View Resulrts in Table
  * Summary Report
  * Aggregate Report
  * Response Time Graph
  * Graph Results
- Assertion 만들기
  * 응답 코드 확인
  * 응답 본문 확인
- CLI 사용하기
  * meter -n -t 설정 파일 -l 리포트 파일

## Chaos Monkey 소개
- [카오스 엔지니어링](http://channy.creation.net/blog/1173) 툴
  * 프로덕션 환경, 특히 분산 시스템 환경에서 불확실성을 파악하고 해결 방안을 모색하는데 사용하는 툴
- 운영 환경 불확실성의 예
  * 네트워크 지연
  * 서버 장애
  * 디스크 오작동
  * 메모리 누수
- [카오스 멍키 스프링 부트](https://codecentric.github.io/chaos-monkey-spring-boot/)
  * 스프링 부트 애플리케이션에 카오스 멍키를 손쉽게 적용해 볼 수 있는 툴
  * 즉, 스프링 부트 애플리케이션을 망가트릴 수 있는 툴
- 카오스 멍키 스프링 부트 주요 개념
 
  ![스크린샷 2021-10-14 오전 5 35 18](https://user-images.githubusercontent.com/18282470/137208791-c274c1ca-8ea3-4d31-b12a-1c5df578b767.png)

## Chaos Monkey 설치
- 의존성 추가

``` xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>chaos-monkey-spring-boot</artifactId>
    <version>2.1.1</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

- Chaos-monkey-spring-boot
  * 스프링 부트용 카오스 멍키 제공
- Spring-boot-starter-actuator
  * 스프링 부트 운영 툴로, 런타임 중에 카오스 멍키 설정을 변경할 수 있다.
  * 그밖에도 헬스 체크, 로그 레벨 변경, 매트릭스 데이터 조회 등 다양한 운영 툴로 사용 가능.
  * /actuator
- 카오스 멍키 활성화
  * spring.profiles.active=chaos-monkey
- 스프링 부트 Actuator 엔드 포인트 활성화

``` properties
management.endpoint.chaosmonkey.enabled=true
management.endpoints.web.exposure.include=health,info,chaosmonkey
```

## CM4SB 응답 지연
- 응답 지연 이슈 재현 방법
  * Repository Watcher 활성화
    * chaos.monkey.watcher.repository=true
  * 카오스 멍키 활성화
    * http post localhost:8080/actuator/chaosmonkey/enable
  * 카오스 멍키 활서화 확인
    * http localhost:8080/actuator/chaosmonkey/status
  * 카오스 멍키 와처 확인
    * http localhost:8080/actuator/chaosmonkey/watchers
  * 카오스 멍키 지연 공격 설정
    * http POST localhost:8080/actuator/chaosmonkey/assaults level=3 latencyRangeStart=2000 latencyRangeEnd=5000 latencyActive=true
  * 테스트
    * JMeter 확인
  * A ->  B1, B2
- 참고:
  * https://codecentric.github.io/chaos-monkey-spring-boot/2.1.1/#_customize_watcher

## CM4SB 에러 발생
- 에러 발생 재현 방법
 
``` bash
http POST localhost:8080/actuator/chaosmonkey/assaults level=3 latencyActive=false exceptionsActive=true exception.type=java.lang.RuntimeException
```

- https://codecentric.github.io/chaos-monkey-spring-boot/2.1.1/#_examples

## ArchUnit 소개
- https://www.archunit.org/
- 애플리케이션의 아키텍처를 테스트 할 수 있는 오픈 소스 라이브러리로, 패키지, 클래스, 레이어, 슬라이스 간의 의존성을 확인할 수 있는 기능을 제공한다.
- 아키텍처 테스트 유즈 케이스
  * A 라는 패키지가 B (또는 C, D) 패키지에서만 사용 되고 있는지 확인 가능.
  * *Serivce라는 이름의 클래스들이 *Controller 또는 *Service라는 이름의 클래스에서만 참조하고 있는지 확인.
  * *Service라는 이름의 클래스들이 ..service.. 라는 패키지에 들어있는지 확인.
  * A라는 애노테이션을 선언한 메소드만 특정 패키지 또는 특정 애노테이션을 가진 클래스를 호출하고 있는지 확인.
  * 특정한 스타일의 아키텍처를 따르고 있는지 확인.
- 참고
  * https://blogs.oracle.com/javamagazine/unit-test-your-architecture-with-archunit
  * https://www.archunit.org/userguide/html/000_Index.html
  * [Moduliths](https://github.com/odrotbohm/moduliths)

## ArchUnit 설치
- https://www.archunit.org/userguide/html/000_Index.html#_junit_5
- JUnit 5용 ArchUnit 설치
``` xml
<dependency>
    <groupId>com.tngtech.archunit</groupId>
    <artifactId>archunit-junit5-engine</artifactId>
    <version>0.12.0</version>
    <scope>test</scope>
</dependency>
```
 
- 주요 사용법
  * 특정 패키지에 해당하는 클래스를 (바이트코드를 통해) 읽어들이고
  * 확인할 규칙을 정의하고
  * 읽어들인 클래스들이 그 규칙을 잘 따르는지 확인한다.

``` java
@Test
public void Services_should_only_be_accessed_by_Controllers() {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("com.mycompany.myapp");

    ArchRule myRule = classes()
        .that().resideInAPackage("..service..")
        .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

    myRule.check(importedClasses);
}
```

- JUnit 5 확장팩 제공
  * @AnalyzeClasses: 클래스를 읽어들여서 확인할 패키지 설정
  * @ArchTest: 확인할 규칙 정의

## ArchUnit: 패키지 의존성 확인하기
- 확인하려는 패키지 구조
 
![스크린샷 2021-10-17 오후 2 23 33](https://user-images.githubusercontent.com/18282470/137613010-bbda9d27-b197-40d0-ae69-fa66ddffcf8c.png)

- 실제로 그런지 확인하려면...
  * ..domain.. 패키지에 있는 클래스는 ..study.., ..member.., ..domain에서 참조 가능.
  * ..member.. 패키지에 있는 클래스는 ..study..와 ..member..에서만 참조 가능.
    * (반대로) ..domain.. 패키지는 ..member.. 패키지를 참조하지 못한다.
  * ..study.. 패키지에 있는 클래스는 ..study.. 에서만 참조 가능.
  * 순환 참조 없어야 한다.

## ArchUnit: JUnit 5 연동하기
- @AnalyzeClasses: 클래스를 읽어들여서 확인할 패키지 설정
- @ArchTest: 확인할 규칙 정의

``` java
@AnalyzeClasses(packagesOf = App.class)
public class ArchTests {

    @ArchTest
    ArchRule domainPackageRule = classes().that().resideInAPackage("..domain..")
            .should().onlyBeAccessed().byClassesThat()
            .resideInAnyPackage("..study..", "..member..", "..domain..");

    @ArchTest
    ArchRule memberPackageRule = noClasses().that().resideInAPackage("..domain..")
            .should().accessClassesThat().resideInAPackage("..member..");

    @ArchTest
    ArchRule studyPackageRule = noClasses().that().resideOutsideOfPackage("..study..")
            .should().accessClassesThat().resideInAnyPackage("..study..");

    @ArchTest
    ArchRule freeOfCycles = slices().matching("..inflearnthejavatest.(*)..")
            .should().beFreeOfCycles();

}
```
