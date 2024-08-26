package com.example.dao;

import com.example.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostRedisDAOTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PostRedisDAO postRedisDAO;

    @BeforeEach
    public void setUp() {
        //redisTemplate.delete("Post"); // Limpa o Redis antes de cada teste
    }

    @Test
    public void testSave() {
        Post post = new Post();
        post.setId(1L);
        post.setTitulo("Test Post");

        postRedisDAO.save(post);

        Post savedPost = (Post) redisTemplate.opsForHash().get("Post", "1");
        assertEquals(post, savedPost);
    }

    @Test
    public void testFindById() {
        Post post = new Post();
        post.setId(1L);
        post.setTitulo("Test Post");

        redisTemplate.opsForHash().put("Post", "1", post);

        Post result = postRedisDAO.findById(1L);

        assertEquals(post, result);
    }

    @Test
    public void testFindAll() {
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitulo("Test Post 1");

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitulo("Test Post 2");

        redisTemplate.opsForHash().put("Post", "1", post1);
        redisTemplate.opsForHash().put("Post", "2", post2);

        Map<Object, Object> result = postRedisDAO.findAll();

        assertEquals(2, result.size());
        assertEquals(post1, result.get("1"));
        assertEquals(post2, result.get("2"));
    }

    @Test
    public void testUpdate() {
        Post post = new Post();
        post.setId(1L);
        post.setTitulo("Test Post");

        redisTemplate.opsForHash().put("Post", "1", post);

        post.setTitulo("Updated Test Post");
        postRedisDAO.update(post);

        Post updatedPost = (Post) redisTemplate.opsForHash().get("Post", "1");
        assertEquals("Updated Test Post", updatedPost.getTitulo());
    }

    @Test
    public void testDelete() {
        Post post = new Post();
        post.setId(1L);
        post.setTitulo("Test Post");

        redisTemplate.opsForHash().put("Post", "1", post);

        postRedisDAO.delete(1L);

        Post deletedPost = (Post) redisTemplate.opsForHash().get("Post", "1");
        assertNull(deletedPost);
    }
}
