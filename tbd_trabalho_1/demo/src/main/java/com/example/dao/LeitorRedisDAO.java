package com.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Leitor;

import java.util.Map;

@Repository
public class LeitorRedisDAO {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY = "Leitor";

    public void save(Leitor leitor) {
        redisTemplate.opsForHash().put(KEY, leitor.getId().toString(), leitor);
    }

    public Leitor findById(Long id) {
        return (Leitor) redisTemplate.opsForHash().get(KEY, id.toString());
    }

    public Map<Object, Object> findAll() {
        return redisTemplate.opsForHash().entries(KEY);
    }

    public void update(Leitor leitor) {
        save(leitor);
    }

    public void delete(Long id) {
        redisTemplate.opsForHash().delete(KEY, id.toString());
    }
}
