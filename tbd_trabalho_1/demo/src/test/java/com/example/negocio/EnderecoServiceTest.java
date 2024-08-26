package com.example.negocio;

import com.example.dao.EnderecoRedisDAO;
import com.example.model.Endereco;
import com.example.persistencia.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private EnderecoRedisDAO enderecoRedisDAO;

    @InjectMocks
    private EnderecoService enderecoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEndereco() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua A");

        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco result = enderecoService.createEndereco(endereco);

        assertEquals(endereco, result);
        verify(enderecoRepository, times(1)).save(endereco);
        verify(enderecoRedisDAO, times(1)).save(endereco);
    }

    @Test
    public void testGetEnderecoWhenNotInRedis() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua A");

        when(enderecoRedisDAO.findById(1L)).thenReturn(null);
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        Endereco result = enderecoService.getEndereco(1L);

        assertEquals(endereco, result);
        verify(enderecoRedisDAO, times(1)).findById(1L);
        verify(enderecoRepository, times(1)).findById(1L);
        verify(enderecoRedisDAO, times(1)).save(endereco);
    }

    @Test
    public void testGetEnderecoWhenInRedis() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua A");

        when(enderecoRedisDAO.findById(1L)).thenReturn(endereco);

        Endereco result = enderecoService.getEndereco(1L);

        assertEquals(endereco, result);
        verify(enderecoRedisDAO, times(1)).findById(1L);
        verify(enderecoRepository, never()).findById(1L);
        verify(enderecoRedisDAO, never()).save(any(Endereco.class));
    }

    @Test
    public void testUpdateEndereco() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua A");

        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco result = enderecoService.updateEndereco(endereco);

        assertEquals(endereco, result);
        verify(enderecoRepository, times(1)).save(endereco);
        verify(enderecoRedisDAO, times(1)).update(endereco);
    }

    @Test
    public void testDeleteEndereco() {
        Long enderecoId = 1L;

        enderecoService.deleteEndereco(enderecoId);

        verify(enderecoRepository, times(1)).deleteById(enderecoId);
        verify(enderecoRedisDAO, times(1)).delete(enderecoId);
    }
}
