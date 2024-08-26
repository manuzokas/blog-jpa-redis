package com.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Endereco;

import java.util.Map;

@Repository
public class EnderecoRedisDAO {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY = "Endereco";

    public void save(Endereco endereco) {
        redisTemplate.opsForHash().put(KEY, endereco.getId().toString(), endereco);
    }

    public Endereco findById(Long id) {
        return (Endereco) redisTemplate.opsForHash().get(KEY, id.toString());
    }

    public Map<Object, Object> findAll() {
        return redisTemplate.opsForHash().entries(KEY);
    }

    public void update(Endereco endereco) {
        save(endereco);
    }

    public void delete(Long id) {
        redisTemplate.opsForHash().delete(KEY, id.toString());
    }
}
