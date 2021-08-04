package spring_web.starter.repository;

import spring_web.starter.domain.Post;

public interface PostRepository {
    Post write(Post post);
}
