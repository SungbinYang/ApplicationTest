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
