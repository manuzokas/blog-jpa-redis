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

import com.example.model.Leitor;
import com.example.negocio.LeitorService;


@RestController
@RequestMapping("/api/leitores")
public class LeitorController {
    @Autowired
    private LeitorService leitorService;

    @PostMapping
    public Leitor createLeitor(@RequestBody Leitor leitor) {
        return leitorService.createLeitor(leitor);
    }

    @GetMapping("/{id}")
    public Leitor getLeitor(@PathVariable Long id) {
        return leitorService.getLeitor(id);
    }

    @PutMapping
    public Leitor updateLeitor(@RequestBody Leitor leitor) {
        return leitorService.updateLeitor(leitor);
    }

    @DeleteMapping("/{id}")
    public void deleteLeitor(@PathVariable Long id) {
        leitorService.deleteLeitor(id);
    }
}
