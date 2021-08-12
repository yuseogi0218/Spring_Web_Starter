package spring_web.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import spring_web.starter.domain.Post;
import spring_web.starter.repository.PostRepository;

import java.util.List;
import java.util.Optional;

public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long write(Post post) {
        postRepository.write(post);
        return post.getId();
    }

    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(Long post_id) {
        return postRepository.findById(post_id);
    }

    public void delete(Long post_id) {
        postRepository.delete(post_id);
    }

    public Optional<Post> update(Long post_id, Post post) {
        return postRepository.update(post_id, post);
    }
}
