package com.example.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.models.museums;
import com.example.backend.repositories.MuseumRepository;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class MuseumController {

    private final MuseumRepository museumRepository;

    public MuseumController(MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
    }

    @GetMapping("/museums")
    public List<museums> getAllMuseums() {
        return (List<museums>) museumRepository.findAll();
    }

    @PostMapping("/museums")
    public ResponseEntity<Object> createMuseum(@RequestBody museums museum) {

        // Проверка на пустое значение поля name  в запросе
        if (Objects.equals(museum.getName(), "")) {
            return new ResponseEntity<>("Имя не должно быть пустым", HttpStatus.BAD_REQUEST);
        }

        // Проверяем, существует ли уже пользователь с таким именем
        if (museumRepository.findByName(museum.getName()).isPresent()) {
            return new ResponseEntity<>("Такое имя уже существует", HttpStatus.BAD_REQUEST);
        }

        museums mm = museumRepository.save(museum);
        return new ResponseEntity<>(mm, HttpStatus.OK);
    }
}