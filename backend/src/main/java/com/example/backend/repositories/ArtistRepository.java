package com.example.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.backend.models.artists;

public interface ArtistRepository  extends CrudRepository<artists, Long> {
    @Query("SELECT c FROM artists c WHERE c.name = :name")
    public Optional<artists> findByName(@Param("name") String name);

    @Query("SELECT c FROM artists c WHERE c.id = :id")
    public Optional<artists> findById(@Param("id") Long id);
}