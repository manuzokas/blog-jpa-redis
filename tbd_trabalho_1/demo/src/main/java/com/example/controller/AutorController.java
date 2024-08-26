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

import com.example.model.Autor;
import com.example.negocio.AutorService;


@RestController
@RequestMapping("/api/autores")
public class AutorController {
    @Autowired
    private AutorService autorService;

    //protocolos/metodos https api rest

    @PostMapping
    public Autor createAutor(@RequestBody Autor autor) {
        return autorService.createAutor(autor);
    }

    @GetMapping("/{id}")
    public Autor getAutor(@PathVariable Long id) {
        return autorService.getAutor(id);
    }

    @PutMapping
    public Autor updateAutor(@RequestBody Autor autor) {
        return autorService.updateAutor(autor);
    }

    @DeleteMapping("/{id}")
    public void deleteAutor(@PathVariable Long id) {
        autorService.deleteAutor(id);
    }
}
