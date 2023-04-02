package com.example.backend.controllers;

import com.example.backend.repositories.MuseumRepository;
import com.example.backend.repositories.UserRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.models.museums;
import com.example.backend.models.users;

import java.util.*;


@RestController
@RequestMapping("/api")
public class UsersController {

    private final UserRepository userRepository;
    private final MuseumRepository museumRepository;

    public UsersController(UserRepository userRepository, MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<users> getAllCountries() {
        return (List<users>) userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody users user) {

        if (Objects.equals(user.getLogin(), "")) {
            return new ResponseEntity<>("Логин не должен быть пустым", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByName(user.getLogin()).isPresent()) {
            return new ResponseEntity<>("Этот логин уже существует", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>("Эта почта уже существует", HttpStatus.BAD_REQUEST);
        }

        users nc = userRepository.save(user);
        return new ResponseEntity<>(nc, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long userId) {
        Optional<users> userToDelete  = userRepository.findById(userId);
        if (userToDelete.isEmpty()) {
            return new ResponseEntity<>("Ошибка удаления", HttpStatus.BAD_REQUEST);
        }
        else {
            userRepository.delete(userToDelete.get());
            return new ResponseEntity<>("Удаление успешно", HttpStatus.OK);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<users> updateUser(@PathVariable(value = "id") Long userId,
                                            @RequestBody users userDetails) {
        Optional <users> uu = userRepository.findById(userId);
        users updateUser = null;
        if (uu.isPresent()) {
            updateUser = uu.get();
            updateUser.login = userDetails.login;
            updateUser.email = userDetails.email;
            userRepository.save(updateUser);
            return ResponseEntity.ok(updateUser);
        }
        else { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"); }
    }


    @PostMapping("/users/{id}/addmuseums")
    public ResponseEntity<Object> addMuseums(@PathVariable(value = "id") Long userId,
                                             @Valid @RequestBody Set<Long> museums_id) {
        Optional<users> uu = userRepository.findById(userId);
        if (uu.isPresent()) {
            users u = uu.get();
            for (Long m : museums_id) {
                Optional<museums> mm = museumRepository.findById(m);
                if (mm.isPresent()) {
                    u.addMuseum(mm.get());
                }
            }
            userRepository.save(u);
        }
        return new ResponseEntity<>("Добавлено успешно", HttpStatus.OK);
    }


    @PostMapping("/users/{id}/removemuseums")
    public ResponseEntity<Object> removeMuseums(@PathVariable(value = "id") Long userId,
                                                @Valid @RequestBody Set<Long> museums_id) {
        Optional<users> uu = userRepository.findById(userId);
        if (uu.isPresent()) {
            users u = uu.get();
            for (Long m : museums_id) {
                Optional<museums> mm = museumRepository.findById(m);
                if (mm.isPresent()) {
                    u.removeMuseum(mm.get());
                    // u.museums.add(mm.get());
                }
            }
            userRepository.save(u);
        }
        return new ResponseEntity<>("Удаление успешно", HttpStatus.OK);
    }
}