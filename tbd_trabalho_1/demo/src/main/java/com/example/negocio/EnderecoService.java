package com.example.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.EnderecoRedisDAO;
import com.example.model.Endereco;
import com.example.persistencia.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoRedisDAO enderecoRedisDAO;

    public Endereco createEndereco(Endereco endereco) {
        Endereco savedEndereco = enderecoRepository.save(endereco);
        enderecoRedisDAO.save(savedEndereco);
        return savedEndereco;
    }

    public Endereco getEndereco(Long id) {
        Endereco endereco = enderecoRedisDAO.findById(id);
        if (endereco == null) {
            endereco = enderecoRepository.findById(id).orElse(null);
            if (endereco != null) {
                enderecoRedisDAO.save(endereco);
            }
        }
        return endereco;
    }

    public Endereco updateEndereco(Endereco endereco) {
        Endereco updatedEndereco = enderecoRepository.save(endereco);
        enderecoRedisDAO.update(updatedEndereco);
        return updatedEndereco;
    }

    public void deleteEndereco(Long id) {
        enderecoRepository.deleteById(id);
        enderecoRedisDAO.delete(id);
    }
}
