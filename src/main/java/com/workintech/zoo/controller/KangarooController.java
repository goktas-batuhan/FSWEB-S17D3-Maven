package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
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
@RequestMapping("/kangaroos")
public class KangarooController {

    public Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> getAll() {
        return new ArrayList<>(kangaroos.values());
    }

    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable int id) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0: " + id, HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id is not found: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo save(@RequestBody Kangaroo kangaroo) {
        if (kangaroo == null || kangaroo.getId() == null || kangaroo.getId() <= 0 ||
                kangaroo.getName() == null || kangaroo.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Kangaroo credentials are not valid");
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable int id, @RequestBody Kangaroo kangaroo) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0: " + id, HttpStatus.BAD_REQUEST);
        }
        if (kangaroo == null || kangaroo.getName() == null || kangaroo.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Kangaroo credentials are not valid");
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id is not found: " + id, HttpStatus.NOT_FOUND);
        }
        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable int id) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0: " + id, HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id is not found: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }
}
