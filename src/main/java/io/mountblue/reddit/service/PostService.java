package io.mountblue.reddit.service;

import io.mountblue.reddit.model.Post;
import io.mountblue.reddit.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public void saveCreatePost(Post post){
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
       return postRepository.findAll();
    }

    public Post getPostById(Long id) {
       return postRepository.findById(id).orElse(null);
    }
}
