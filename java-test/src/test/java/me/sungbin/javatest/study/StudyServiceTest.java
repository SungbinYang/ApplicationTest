package me.sungbin.javatest.study;

import me.sungbin.javatest.domain.Member;
import me.sungbin.javatest.domain.Study;
import me.sungbin.javatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock MemberService memberService;
    
    @Mock StudyRepository studyRepository;

    @Test
    void createNewStudy() {

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
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());

//        studyService.createNewStudy(1L, study);
    }
}