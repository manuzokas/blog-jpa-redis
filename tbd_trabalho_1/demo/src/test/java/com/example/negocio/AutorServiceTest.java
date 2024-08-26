package com.example.negocio;

import com.example.dao.AutorRedisDAO;
import com.example.model.Autor;
import com.example.persistencia.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorRedisDAO autorRedisDAO;

    @InjectMocks
    private AutorService autorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAutor() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Author Name");

        when(autorRepository.save(autor)).thenReturn(autor);

        Autor result = autorService.createAutor(autor);

        assertEquals(autor, result);
        verify(autorRepository, times(1)).save(autor);
        verify(autorRedisDAO, times(1)).save(autor);
    }

    @Test
    public void testGetAutorWhenNotInRedis() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Author Name");

        when(autorRedisDAO.findById(1L)).thenReturn(null);
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        Autor result = autorService.getAutor(1L);

        assertEquals(autor, result);
        verify(autorRedisDAO, times(1)).findById(1L);
        verify(autorRepository, times(1)).findById(1L);
        verify(autorRedisDAO, times(1)).save(autor);
    }

    @Test
    public void testGetAutorWhenInRedis() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Author Name");

        when(autorRedisDAO.findById(1L)).thenReturn(autor);

        Autor result = autorService.getAutor(1L);

        assertEquals(autor, result);
        verify(autorRedisDAO, times(1)).findById(1L);
        verify(autorRepository, never()).findById(1L);
        verify(autorRedisDAO, never()).save(any(Autor.class));
    }

    @Test
    public void testUpdateAutor() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Author Name");

        when(autorRepository.save(autor)).thenReturn(autor);

        Autor result = autorService.updateAutor(autor);

        assertEquals(autor, result);
        verify(autorRepository, times(1)).save(autor);
        verify(autorRedisDAO, times(1)).update(autor);
    }

    @Test
    public void testDeleteAutor() {
        Long autorId = 1L;

        autorService.deleteAutor(autorId);

        verify(autorRepository, times(1)).deleteById(autorId);
        verify(autorRedisDAO, times(1)).delete(autorId);
    }
}
