package com.example.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.models.countries;
import com.example.backend.repositories.CountryRepository;
import org.springframework.web.server.ResponseStatusException;


import java.util.*;


@RestController
@RequestMapping("/api")
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/countries")
    public List
    getAllCountries() {
        return (List) countryRepository.findAll();
    }

    @PostMapping("/countries")
    public ResponseEntity<Object> createCountry(@RequestBody countries country) {

        if (Objects.equals(country.getName(), "")) {
            return new ResponseEntity<>("Поле \"name\" не может быть пустым", HttpStatus.BAD_REQUEST);
        }

        Optional<countries> existingCountry = countryRepository.findByName(country.getName());
        if (existingCountry.isPresent()) {
            System.out.println(existingCountry);
            return new ResponseEntity<>("Такая страна уже существует", HttpStatus.BAD_REQUEST);
        }

        countries nc = countryRepository.save(country);
        return new ResponseEntity<>(nc, HttpStatus.OK);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<String> updateCountry(@PathVariable(value = "id") Long countryId,
                                                @RequestBody countries countryDetails) {

        if (Objects.equals(countryDetails.getName(), "")) {
            return new ResponseEntity<>("Поле \"name\" не может быть пустым", HttpStatus.BAD_REQUEST);
        }

        Optional<countries> existingCountry = countryRepository.findById(countryId);
        if (existingCountry.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Страны с таким id не существует");
        }
        else {
            countries newCountry = existingCountry.get();
            newCountry.name = countryDetails.name;
            countryRepository.save(newCountry);
            return ResponseEntity.ok("Успешно сохранено");
        }
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Object> deleteCountry(@PathVariable(value = "id") Long countryId) {
        Optional<countries> countryToDelete  = countryRepository.findById(countryId);
        if (countryToDelete.isEmpty()) {
            return new ResponseEntity<>("Ошибка удаления", HttpStatus.OK);
        }
        else {
            countryRepository.delete(countryToDelete.get());
            return new ResponseEntity<>("Успешно удалено", HttpStatus.OK);
        }
    }
}