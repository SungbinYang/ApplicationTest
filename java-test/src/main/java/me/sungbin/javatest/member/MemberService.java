package me.sungbin.javatest.member;

import me.sungbin.javatest.domain.Member;
import me.sungbin.javatest.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
