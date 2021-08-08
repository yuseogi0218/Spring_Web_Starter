package spring_web.starter.repository;

import spring_web.starter.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post write(Post post);

    List<Post> findAll();

    Optional<Post> findById(Long id);

}
