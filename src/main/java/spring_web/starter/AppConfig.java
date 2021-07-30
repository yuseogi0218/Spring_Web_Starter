package spring_web.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring_web.starter.repository.JpaMemberRepository;
import spring_web.starter.repository.MemberRepository;
import spring_web.starter.service.MemberService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class AppConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
}
