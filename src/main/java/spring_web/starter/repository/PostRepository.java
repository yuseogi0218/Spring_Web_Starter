package spring_web.starter.repository;

import spring_web.starter.domain.Post;

import java.util.List;

public interface PostRepository {
    Post write(Post post);
    List<Post> findAll();
}
