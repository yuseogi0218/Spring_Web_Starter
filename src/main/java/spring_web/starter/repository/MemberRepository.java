package spring_web.starter.repository;

import spring_web.starter.domain.Member;

import java.util.Optional;

public interface MemberRepository {

    Member signUp(Member member);

    Optional<Member> findById(String user_id);

    String login(String user_id, String user_pass);

    String signOut(String user_id);
}
