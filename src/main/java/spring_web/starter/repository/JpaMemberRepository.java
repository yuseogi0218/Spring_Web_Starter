package spring_web.starter.repository;

import org.springframework.stereotype.Repository;
import spring_web.starter.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member signUp(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(String user_id) {
        List<Member> result = em.createQuery("select m from Member m where m.user_id = :user_id", Member.class)
                .setParameter("user_id", user_id)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public String login(String user_id, String user_pass) {
        List<Member> result = em.createQuery("select m from Member m where m.user_id = :user_id", Member.class)
                .setParameter("user_id", user_id)
                .getResultList();

        Optional<Member> member = result.stream().findAny();

        if (member.isPresent()) {
            Member result_member = member.get();
            if (user_pass.equals(result_member.getUser_pass())) {
                return result_member.getUser_name();
            } else {
                return "pass_error";
            }
        }else{
            return "null";
        }

    }

}
