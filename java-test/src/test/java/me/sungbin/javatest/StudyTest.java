package me.sungbin.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

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
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(10);
        });
        Study actual = new Study(10);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

//    @DisabledOnOs(OS.MAC)
    @SlowTest
    @DisplayName("스터디 만들기 slow")
//    @EnabledOnJre(JRE.OTHER)
//    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "sungbin")
    void create_new_study_again() {
        System.out.println("create1");
    }

    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    @DisplayName("반복 테스트")
    void repeatStudy(RepetitionInfo repetitionInfo) {
        System.out.println("test" + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."})
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before All");
    }

    @AfterAll
    static void afterAll() {
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