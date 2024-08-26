package com.example.negocio;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.dao.LeitorRedisDAO;
import com.example.model.Leitor;
import com.example.persistencia.LeitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LeitorServiceTest {

    @InjectMocks
    private LeitorService leitorService;

    @Mock
    private LeitorRepository leitorRepository;

    @Mock
    private LeitorRedisDAO leitorRedisDAO;

    private Leitor leitor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        leitor = new Leitor();
        leitor.setId(1L);
        leitor.setNome("Leitor Teste");
        leitor.setEmail("leitor@teste.com");
        leitor.setSenha("senha123");
    }

    @Test
    public void testCreateLeitor() {
        when(leitorRepository.save(any(Leitor.class))).thenReturn(leitor);
        doNothing().when(leitorRedisDAO).save(any(Leitor.class));

        Leitor createdLeitor = leitorService.createLeitor(leitor);
        assertNotNull(createdLeitor);
        assertEquals(leitor.getId(), createdLeitor.getId());
        verify(leitorRedisDAO, times(1)).save(leitor);
    }

    @Test
    public void testGetLeitor() {
        when(leitorRedisDAO.findById(anyLong())).thenReturn(leitor);
        when(leitorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(leitor));

        Leitor fetchedLeitor = leitorService.getLeitor(1L);
        assertNotNull(fetchedLeitor);
        assertEquals(leitor.getId(), fetchedLeitor.getId());
    }

    // Adicione mais testes para update e delete conforme necessário
    @Test
    public void testUpdateLeitor() {
        // Configura o comportamento do mock para retorno do leitor atualizado
        Leitor updatedLeitor = new Leitor();
        updatedLeitor.setId(1L);
        updatedLeitor.setNome("Leitor Atualizado");
        updatedLeitor.setEmail("leitor@atualizado.com");
        updatedLeitor.setSenha("senha456");

        when(leitorRepository.save(any(Leitor.class))).thenReturn(updatedLeitor);
        doNothing().when(leitorRedisDAO).update(any(Leitor.class));

        Leitor resultLeitor = leitorService.updateLeitor(updatedLeitor);
        assertNotNull(resultLeitor);
        assertEquals(updatedLeitor.getId(), resultLeitor.getId());
        assertEquals("Leitor Atualizado", resultLeitor.getNome());
        assertEquals("leitor@atualizado.com", resultLeitor.getEmail());
        assertEquals("senha456", resultLeitor.getSenha());

        verify(leitorRedisDAO, times(1)).update(updatedLeitor);
    }

    @Test
    public void testDeleteLeitor() {
        // Configura o comportamento do mock para a exclusão do leitor
        doNothing().when(leitorRepository).deleteById(anyLong());
        doNothing().when(leitorRedisDAO).delete(anyLong());

        // Chama o método de exclusão
        leitorService.deleteLeitor(1L);

        // Verifica se os métodos de exclusão foram chamados
        verify(leitorRepository, times(1)).deleteById(1L);
        verify(leitorRedisDAO, times(1)).delete(1L);
    }

}
