package com.example.dao;

import com.example.model.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EnderecoRedisDAOTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private EnderecoRedisDAO enderecoRedisDAO;

    @BeforeEach
    public void setUp() {
        //redisTemplate.delete("Endereco"); // Limpa o Redis antes de cada teste
    }

    @Test
    public void testSave() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua A");

        enderecoRedisDAO.save(endereco);

        Endereco savedEndereco = (Endereco) redisTemplate.opsForHash().get("Endereco", "1");
        assertEquals(endereco, savedEndereco);
    }

    @Test
    public void testFindById() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua A");

        redisTemplate.opsForHash().put("Endereco", "1", endereco);

        Endereco result = enderecoRedisDAO.findById(1L);

        assertEquals(endereco, result);
    }

    @Test
    public void testFindAll() {
        Endereco endereco1 = new Endereco();
        endereco1.setId(1L);
        endereco1.setRua("Rua A");

        Endereco endereco2 = new Endereco();
        endereco2.setId(2L);
        endereco2.setRua("Rua B");

        redisTemplate.opsForHash().put("Endereco", "1", endereco1);
        redisTemplate.opsForHash().put("Endereco", "2", endereco2);

        Map<Object, Object> result = enderecoRedisDAO.findAll();

        assertEquals(2, result.size());
        assertEquals(endereco1, result.get("1"));
        assertEquals(endereco2, result.get("2"));
    }

    @Test
    public void testUpdate() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua A");

        redisTemplate.opsForHash().put("Endereco", "1", endereco);

        endereco.setRua("Rua Atualizada");
        enderecoRedisDAO.update(endereco);

        Endereco updatedEndereco = (Endereco) redisTemplate.opsForHash().get("Endereco", "1");
        assertEquals("Rua Atualizada", updatedEndereco.getRua());
    }

    @Test
    public void testDelete() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua A");

        redisTemplate.opsForHash().put("Endereco", "1", endereco);

        enderecoRedisDAO.delete(1L);

        Endereco deletedEndereco = (Endereco) redisTemplate.opsForHash().get("Endereco", "1");
        assertNull(deletedEndereco);
    }
}
