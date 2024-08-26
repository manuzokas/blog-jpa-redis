package com.example.dao;

import com.example.model.Autor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AutorRedisDAOTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AutorRedisDAO autorRedisDAO;

    @BeforeEach
    public void setUp() {
        //redisTemplate.delete("Autor"); // Limpa o Redis antes de cada teste
    }

    @Test
    public void testSave() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Author Name");

        autorRedisDAO.save(autor);

        Autor savedAutor = (Autor) redisTemplate.opsForHash().get("Autor", "1");
        assertEquals(autor, savedAutor);
    }

    @Test
    public void testFindById() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Author Name");

        redisTemplate.opsForHash().put("Autor", "1", autor);

        Autor result = autorRedisDAO.findById(1L);

        assertEquals(autor, result);
    }

    @Test
    public void testFindAll() {
        Autor autor1 = new Autor();
        autor1.setId(1L);
        autor1.setNome("Author Name 1");

        Autor autor2 = new Autor();
        autor2.setId(2L);
        autor2.setNome("Author Name 2");

        redisTemplate.opsForHash().put("Autor", "1", autor1);
        redisTemplate.opsForHash().put("Autor", "2", autor2);

        Map<Object, Object> result = autorRedisDAO.findAll();

        assertEquals(2, result.size());
        assertEquals(autor1, result.get("1"));
        assertEquals(autor2, result.get("2"));
    }

    @Test
    public void testUpdate() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Author Name");

        redisTemplate.opsForHash().put("Autor", "1", autor);

        autor.setNome("Updated Author Name");
        autorRedisDAO.update(autor);

        Autor updatedAutor = (Autor) redisTemplate.opsForHash().get("Autor", "1");
        assertEquals("Updated Author Name", updatedAutor.getNome());
    }

    @Test
    public void testDelete() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Author Name");

        redisTemplate.opsForHash().put("Autor", "1", autor);

        autorRedisDAO.delete(1L);

        Autor deletedAutor = (Autor) redisTemplate.opsForHash().get("Autor", "1");
        assertNull(deletedAutor);
    }
}
