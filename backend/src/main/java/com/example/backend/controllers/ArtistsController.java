package com.example.backend.controllers;

import com.example.backend.models.artists;
import com.example.backend.models.countries;
import com.example.backend.repositories.ArtistRepository;
import com.example.backend.repositories.CountryRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class ArtistsController {

    private final ArtistRepository artistRepository;
    private final CountryRepository countryRepository;

    public ArtistsController(ArtistRepository artistRepository, CountryRepository countryRepository) {
        this.artistRepository = artistRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/artists")
    public List<artists> getAllArtists() {
        return (List<artists>) artistRepository.findAll();
    }

    @PostMapping("/artists")
    public ResponseEntity<Object> createArtist(@RequestBody artists artist) {

        if (Objects.equals(artist.getName(), "")) {
            return new ResponseEntity<>("Имя не должно быть пустым", HttpStatus.BAD_REQUEST);
        }

        if (artistRepository.findByName(artist.getName()).isPresent()) {
            return new ResponseEntity<>("Такой артист уже существует", HttpStatus.BAD_REQUEST);
        }

        Optional<countries> artist_country = countryRepository.findById(artist.country.id);
        if (artist_country.isEmpty()) {
            return new ResponseEntity<>("Это страна для артиста не существует", HttpStatus.BAD_REQUEST);
        }

        artist.country = artist_country.get();
        artists nc = artistRepository.save(artist);
        return new ResponseEntity<Object>(nc, HttpStatus.OK);
    }

}