package com.example.dao;

import com.example.model.Leitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LeitorRedisDAOTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private LeitorRedisDAO leitorRedisDAO;

    @BeforeEach
    public void setUp() {
        //redisTemplate.delete("Leitor"); // Limpa o Redis antes de cada teste
    }

    @Test
    public void testSaveAndFindById() {
        Leitor leitor = new Leitor();
        leitor.setId(1L);
        leitor.setNome("Leitor Teste");
        leitor.setEmail("leitor@teste.com");
        leitor.setSenha("senha123");

        leitorRedisDAO.save(leitor);

        Leitor foundLeitor = leitorRedisDAO.findById(1L);
        assertNotNull(foundLeitor);
        assertEquals("Leitor Teste", foundLeitor.getNome());

    }

    @Test
    public void testSaveAndFindById_SecondLeitor() {
        Leitor leitor = new Leitor();
        leitor.setId(2L);
        leitor.setNome("Teste Leitor Redis");
        leitor.setEmail("teste@leitor.com");
        leitor.setSenha("senha456");

        leitorRedisDAO.save(leitor);

        Leitor foundLeitor = leitorRedisDAO.findById(2L);
        assertNotNull(foundLeitor);
        assertEquals("Teste Leitor Redis", foundLeitor.getNome());
    }

    @Test
    public void testUpdateLeitor() {
        Leitor leitor = new Leitor();
        leitor.setId(1L);
        leitor.setNome("Leitor Teste");
        leitor.setEmail("leitor@teste.com");
        leitor.setSenha("senha123");

        leitorRedisDAO.save(leitor);

        // Atualiza o nome do leitor
        leitor.setNome("Leitor Teste Atualizado");
        leitorRedisDAO.update(leitor);

        Leitor updatedLeitor = leitorRedisDAO.findById(1L);
        assertNotNull(updatedLeitor);
        assertEquals("Leitor Teste Atualizado", updatedLeitor.getNome());

    }

    @Test
    public void testDeleteLeitor() {
        Leitor leitor = new Leitor();
        leitor.setId(3L);
        leitor.setNome("Leitor para Deletar");
        leitor.setEmail("delete@leitor.com");
        leitor.setSenha("senha789");

        leitorRedisDAO.save(leitor);
        leitorRedisDAO.delete(3L);

        Leitor deletedLeitor = leitorRedisDAO.findById(3L);
        assertNull(deletedLeitor);

    }

    @Test
    public void testFindAll() {
        Leitor leitor1 = new Leitor();
        leitor1.setId(1L);
        leitor1.setNome("Leitor Teste");
        leitor1.setEmail("leitor@teste.com");
        leitor1.setSenha("senha123");

        Leitor leitor2 = new Leitor();
        leitor2.setId(2L);
        leitor2.setNome("Teste Leitor Redis");
        leitor2.setEmail("teste@leitor.com");
        leitor2.setSenha("senha456");

        leitorRedisDAO.save(leitor1);
        leitorRedisDAO.save(leitor2);

        var leitores = leitorRedisDAO.findAll();
        assertNotNull(leitores);
        assertTrue(leitores.size() >= 2);
        assertEquals("Leitor Teste", ((Leitor) leitores.get("1")).getNome());
        assertEquals("Teste Leitor Redis", ((Leitor) leitores.get("2")).getNome());

    }
}
