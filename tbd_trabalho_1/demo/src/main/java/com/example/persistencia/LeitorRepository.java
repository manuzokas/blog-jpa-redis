package com.example.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Leitor;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {
}
