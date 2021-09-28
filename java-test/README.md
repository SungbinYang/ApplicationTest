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
