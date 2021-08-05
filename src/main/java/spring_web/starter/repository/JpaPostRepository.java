package spring_web.starter.repository;

import org.springframework.transaction.annotation.Transactional;
import spring_web.starter.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaPostRepository implements PostRepository{

    private final EntityManager em;

    public JpaPostRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @Override
    public Post write(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }
}
