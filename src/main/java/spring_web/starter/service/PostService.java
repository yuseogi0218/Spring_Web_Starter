package spring_web.starter.service;

import spring_web.starter.domain.Post;
import spring_web.starter.repository.PostRepository;

import java.util.List;
import java.util.Optional;

public class PostService {

    private final PostRepository postRepository;

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
}
