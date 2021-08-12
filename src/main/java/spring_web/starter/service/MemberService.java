package spring_web.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import spring_web.starter.domain.Member;
import spring_web.starter.repository.MemberRepository;


@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long signUp(Member member) {
        validateDuplicateMember(member);
        memberRepository.signUp(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getUser_id())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public String login(String user_id, String user_pass) {
        return memberRepository.login(user_id, user_pass);
    }

    public String signOut(String user_id) {
        return memberRepository.signOut(user_id);
    }
}
