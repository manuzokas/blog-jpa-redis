package com.example.negocio;
import com.example.dao.PostRedisDAO;
import com.example.model.Post;
import com.example.persistencia.PostRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostRedisDAO postRedisDAO;

    public Post createPost(Post post) {
        Post savedPost = postRepository.save(post);
        postRedisDAO.save(savedPost);
        return savedPost;
    }

    public Post getPost(Long id) {
        Post post = postRedisDAO.findById(id);
        if (post == null) {
            post = postRepository.findById(id).orElse(null);
            if (post != null) {
                postRedisDAO.save(post);
            }
        }
        return post;
    }

    public Post updatePost(Post post) {
        Post updatedPost = postRepository.save(post);
        postRedisDAO.update(updatedPost);
        return updatedPost;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
        postRedisDAO.delete(id);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
