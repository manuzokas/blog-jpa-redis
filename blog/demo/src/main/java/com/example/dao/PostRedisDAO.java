package com.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Post;

import java.util.Map;

@Repository
public class PostRedisDAO {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY = "Post";

    public void save(Post post) {
        redisTemplate.opsForHash().put(KEY, post.getId().toString(), post);
    }

    public Post findById(Long id) {
        return (Post) redisTemplate.opsForHash().get(KEY, id.toString());
    }

    public Map<Object, Object> findAll() {
        return redisTemplate.opsForHash().entries(KEY);
    }

    public void update(Post post) {
        save(post);
    }

    public void delete(Long id) {
        redisTemplate.opsForHash().delete(KEY, id.toString());
    }
}
