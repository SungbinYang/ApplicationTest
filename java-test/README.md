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
