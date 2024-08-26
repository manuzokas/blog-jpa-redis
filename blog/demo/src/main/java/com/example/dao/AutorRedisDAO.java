package com.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Autor;

import java.util.Map;

@Repository
public class AutorRedisDAO {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY = "Autor";

    public void save(Autor autor) {
        redisTemplate.opsForHash().put(KEY, autor.getId().toString(), autor);
    }

    public Autor findById(Long id) {
        return (Autor) redisTemplate.opsForHash().get(KEY, id.toString());
    }

    public Map<Object, Object> findAll() {
        return redisTemplate.opsForHash().entries(KEY);
    }

    public void update(Autor autor) {
        save(autor);
    }

    public void delete(Long id) {
        redisTemplate.opsForHash().delete(KEY, id.toString());
    }
}
