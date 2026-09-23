package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    public Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> getAll() {
        return new ArrayList<>(koalas.values());
    }

    @GetMapping("/{id}")
    public Koala getById(@PathVariable int id) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0: " + id, HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id is not found: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    public Koala save(@RequestBody Koala koala) {
        if (koala == null || koala.getId() == null || koala.getId() <= 0 ||
                koala.getName() == null || koala.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Koala credentials are not valid");
        }
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable int id, @RequestBody Koala koala) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0: " + id, HttpStatus.BAD_REQUEST);
        }
        if (koala == null || koala.getName() == null || koala.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Koala credentials are not valid");
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id is not found: " + id, HttpStatus.NOT_FOUND);
        }
        koala.setId(id);
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable int id) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0: " + id, HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id is not found: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.remove(id);
    }
}
