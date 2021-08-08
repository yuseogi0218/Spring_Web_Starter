package spring_web.starter.repository;

import org.springframework.transaction.annotation.Transactional;
import spring_web.starter.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Post> findById(Long id) {
        List<Post> result = em.createQuery("select p from Post p where p.id = :id", Post.class)
                .setParameter("id", id)
                .getResultList();
        return result.stream().findAny();
    }
}
