package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.Endereco;
import com.example.negocio.EnderecoService;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public Endereco createEndereco(@RequestBody Endereco endereco) {
        return enderecoService.createEndereco(endereco);
    }

    @GetMapping("/{id}")
    public Endereco getEndereco(@PathVariable Long id) {
        return enderecoService.getEndereco(id);
    }

    @PutMapping
    public Endereco updateEndereco(@RequestBody Endereco endereco) {
        return enderecoService.updateEndereco(endereco);
    }

    @DeleteMapping("/{id}")
    public void deleteEndereco(@PathVariable Long id) {
        enderecoService.deleteEndereco(id);
    }
}
