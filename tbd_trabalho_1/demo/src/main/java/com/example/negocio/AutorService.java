package com.example.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.AutorRedisDAO;
import com.example.model.Autor;
import com.example.persistencia.AutorRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorRedisDAO autorRedisDAO;

    public Autor createAutor(Autor autor) {
        Autor savedAutor = autorRepository.save(autor);
        autorRedisDAO.save(savedAutor);
        return savedAutor;
    }

    public Autor getAutor(Long id) {
        Autor autor = autorRedisDAO.findById(id);
        if (autor == null) {
            autor = autorRepository.findById(id).orElse(null);
            if (autor != null) {
                autorRedisDAO.save(autor);
            }
        }
        return autor;
    }

    public Autor updateAutor(Autor autor) {
        Autor updatedAutor = autorRepository.save(autor);
        autorRedisDAO.update(updatedAutor);
        return updatedAutor;
    }

    public void deleteAutor(Long id) {
        autorRepository.deleteById(id);
        autorRedisDAO.delete(id);
    }
}
