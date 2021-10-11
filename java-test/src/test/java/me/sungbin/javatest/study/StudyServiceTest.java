package me.sungbin.javatest.study;

import lombok.extern.slf4j.Slf4j;
import me.sungbin.javatest.domain.Member;
import me.sungbin.javatest.domain.Study;
import me.sungbin.javatest.member.MemberService;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
class StudyServiceTest {

    @Mock MemberService memberService;

    @Autowired StudyRepository studyRepository;

    @Container
    static GenericContainer postgreSQLContainer = new GenericContainer("postgres")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "studytest")
            .withEnv("POSTGRES_PASSWORD", "studytest");

    @BeforeAll
    static void beforeAll() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        postgreSQLContainer.followOutput(logConsumer);
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("==============================");
        System.out.println(postgreSQLContainer.getMappedPort(5432));

        studyRepository.deleteAll();
    }

    @Test
    @DisplayName("새로운 스터디를 만드는 테스트")
    void createNewStudy() {

        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("sungbin@email.com");

//        when(memberService.findById(any())).thenReturn(Optional.of(member));

//        assertEquals("sungbin@email.com", memberService.findById(1L).get().getEmail());
//        assertEquals("sungbin@email.com", memberService.findById(2L).get().getEmail());
//
//        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            memberService.validate(1L);
//        });
//
//        memberService.validate(2L);

//        when(memberService.findById(any()))
//                .thenReturn(Optional.of(member))
//                .thenThrow(new RuntimeException())
//                .thenReturn(Optional.empty());
//
//        Optional<Member> byId = memberService.findById(1L);
//        assertEquals("sungbin@email.com", byId.get().getEmail());
//
//        assertThrows(RuntimeException.class, () -> {
//            memberService.findById(1L);
//        });
//
//        assertEquals(Optional.empty(), memberService.findById(3L));

        Study study = new Study(10, "테스트");

//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
//        when(studyRepository.save(study)).thenReturn(study);

        given(memberService.findById(1L)).willReturn(Optional.of(member));
//        given(studyRepository.save(study)).willReturn(study);

        // when
        studyService.createNewStudy(1L, study);

        // Then
        assertEquals(1L, study.getOwnerId());

//        verify(memberService, times(1)).notify(study);
        then(memberService).should(times(1)).notify(study);
//        verify(memberService, times(1)).notify(member);

//        verify(memberService, never()).validate(any());

//        InOrder inOrder = inOrder(memberService);
//        inOrder.verify(memberService).notify(study);

//        verifyNoMoreInteractions(memberService);
        then(memberService).shouldHaveNoMoreInteractions();

//        inOrder.verify(memberService).notify(member);// 메소드 호출 순서를 확인한다.

//        studyService.createNewStudy(1L, study);
    }

    @Test
    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
        assertNull(study.getOpenedDateTime());

        // When
        studyService.openStudy(study);

        // Then
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertNotNull(study.getOpenedDateTime());
        then(memberService).should().notify(study);
    }
}