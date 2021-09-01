package spring_web.starter.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import spring_web.starter.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaPostRepository implements PostRepository{

    private final EntityManager em;

    @Autowired
    public JpaPostRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public int findAllCnt() {
        return ((Number) em.createQuery("select count(p) from Post p")
                .getSingleResult()).intValue();
    }

    @Override
    public List<Post> findListPaging(int startIndex, int pageSize) {
        return em.createQuery("select p from Post p ORDER BY p.id DESC", Post.class)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();
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


    @Override
    public void delete(Long id) {
        em.createQuery("delete from Post p where p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional
    @Modifying(clearAutomatically = true)
    public Optional<Post> update(Long id, Post post) {
        String get_post_title = post.getTitle();
        String get_post_body = post.getBody();

        em.createQuery("update Post p set p.title = :get_post_title, p.body = :get_post_body where p.id = :id")
                .setParameter("id", id)
                .setParameter("get_post_body", get_post_body)
                .setParameter("get_post_title", get_post_title)
                .executeUpdate();

        Optional<Post> founded_post = findById(id);
        return founded_post;
    }

    @Override
    @Transactional
    @Modifying(clearAutomatically = true)
    public void view(Long id) {
        em.createQuery("update Post p set p.view = p.view+1 where p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
