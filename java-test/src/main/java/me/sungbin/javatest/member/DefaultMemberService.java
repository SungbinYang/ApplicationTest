package me.sungbin.javatest.member;

import me.sungbin.javatest.domain.Member;
import me.sungbin.javatest.domain.Study;
import me.sungbin.javatest.study.StudyService;

import java.util.Optional;

public class DefaultMemberService implements MemberService {

    StudyService studyService;

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.empty();
    }

    @Override
    public void validate(Long memberId) {
        studyService.hi();
    }

    @Override
    public void notify(Study newStudy) {
        studyService.hi();
    }

    @Override
    public void notify(Member member) {

    }
}
