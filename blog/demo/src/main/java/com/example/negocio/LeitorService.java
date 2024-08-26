package com.example.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.LeitorRedisDAO;
import com.example.model.Leitor;
import com.example.persistencia.LeitorRepository;

@Service
public class LeitorService {

    @Autowired
    private LeitorRepository leitorRepository;

    @Autowired
    private LeitorRedisDAO leitorRedisDAO;

    public Leitor createLeitor(Leitor leitor) {
        Leitor savedLeitor = leitorRepository.save(leitor);
        leitorRedisDAO.save(savedLeitor);
        return savedLeitor;
    }

    public Leitor getLeitor(Long id) {
        Leitor leitor = leitorRedisDAO.findById(id);
        if (leitor == null) {
            leitor = leitorRepository.findById(id).orElse(null);
            if (leitor != null) {
                leitorRedisDAO.save(leitor);
            }
        }
        return leitor;
    }

    public Leitor updateLeitor(Leitor leitor) {
        Leitor updatedLeitor = leitorRepository.save(leitor);
        leitorRedisDAO.update(updatedLeitor);
        return updatedLeitor;
    }

    public void deleteLeitor(Long id) {
        leitorRepository.deleteById(id);
        leitorRedisDAO.delete(id);
    }
}
