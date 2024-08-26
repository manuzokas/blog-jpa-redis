package com.example.negocio;

import com.example.dao.PostRedisDAO;
import com.example.model.Post;
import com.example.persistencia.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostRedisDAO postRedisDAO;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost() {
        Post post = new Post();
        post.setId(1L);
        post.setTitulo("Test Post");

        when(postRepository.save(any(Post.class))).thenReturn(post);

        postService.createPost(post);

        verify(postRepository, times(1)).save(post);
        verify(postRedisDAO, times(1)).save(post);
    }

    @Test
    public void testGetPost() {
        Post post = new Post();
        post.setId(1L);
        post.setTitulo("Test Post");

        when(postRedisDAO.findById(1L)).thenReturn(post);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post result = postService.getPost(1L);

        assertEquals(post, result);
        verify(postRedisDAO, times(1)).findById(1L);
    }

    @Test
    public void testUpdatePost() {
        Post post = new Post();
        post.setId(1L);
        post.setTitulo("Updated Post");

        when(postRepository.save(any(Post.class))).thenReturn(post);

        postService.updatePost(post);

        verify(postRepository, times(1)).save(post);
        verify(postRedisDAO, times(1)).update(post);
    }

    @Test
    public void testDeletePost() {
        Long postId = 1L;

        postService.deletePost(postId);

        verify(postRepository, times(1)).deleteById(postId);
        verify(postRedisDAO, times(1)).delete(postId);
    }

}
