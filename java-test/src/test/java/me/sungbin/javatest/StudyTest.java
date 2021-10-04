package me.sungbin.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudyTest {

    int value = 1;

    @FastTest
    @DisplayName("스터디 만들기 fast")
//    @EnabledOnOs({OS.MAC, OS.LINUX})
//    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_11, JRE.JAVA_15})
//    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void create_new_study() {

//        String test_env = System.getenv("TEST_ENV");
//        System.out.println(test_env);
//        assumeTrue("LOCAL".equalsIgnoreCase(test_env)); // 로컬인 경우에만 다음 테스트 코드 출력

//        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
//            System.out.println("LOCAL");
//            Study actual = new Study(10);
//            assertThat(actual.getLimit()).isGreaterThan(0);
//        });
//
//        assumingThat("sungbin".equalsIgnoreCase(test_env), () -> {
//            System.out.println("sungbin");
//            Study actual = new Study(100);
//            assertThat(actual.getLimit()).isGreaterThan(0);
//        });

//        Study study = new Study(10);
//        assertAll(
//                () -> assertNotNull(study),
//                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다."),
//                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다."),
//                () -> assertThrows(IllegalArgumentException.class, () -> new Study(-10))
//        );
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
//        String message = exception.getMessage();
//        assertEquals("limit은 0보다 커야한다.", exception.getMessage());
//        assertTimeout(Duration.ofMillis(100), () -> {
//            new Study(10);
//            Thread.sleep(10);
//        });
        System.out.println(this);
        System.out.println(value++);
        Study actual = new Study(1);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

//    @DisabledOnOs(OS.MAC)
    @SlowTest
    @DisplayName("스터디 만들기 slow")
//    @EnabledOnJre(JRE.OTHER)
//    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "sungbin")
    void create_new_study_again() {
        System.out.println(this);
        System.out.println("create1 " + value++);
    }

    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    @DisplayName("반복 테스트")
    void repeatStudy(RepetitionInfo repetitionInfo) {
        System.out.println("test" + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

//    //    @ValueSource(ints = {10, 20, 40})
//    //    @NullAndEmptySource
//    @DisplayName("파라미터 반복 테스트")
//    @ParameterizedTest(name = "{index} {displayName} message={0}")
//    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
//    void parameterizedTest(ArgumentsAccessor argumentsAccessor) {
//        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
//        System.out.println(study);
//    }

    //    @ValueSource(ints = {10, 20, 40})
    //    @NullAndEmptySource
    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }

    // ValueSource의 매개뱐수의 타입을 메소드 파라미터에 있는 타입으로 변환
    // 하나의 Argument에만 변환가능하고 2개이상일 경우 ArgumentAggregator로 사용가능하다.
    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            assertEquals(Study.class, aClass, "Can only convert to Study");
            return new Study(Integer.parseInt(o.toString()));
        }
    }

    @BeforeAll
    void beforeAll() {
        System.out.println("before All");
    }

    @AfterAll
    void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before Each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }
}