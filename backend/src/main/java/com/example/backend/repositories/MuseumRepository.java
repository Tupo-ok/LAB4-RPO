package com.example.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.backend.models.museums;

import java.util.Optional;

public interface MuseumRepository extends CrudRepository<museums, Long>{
    @Query("SELECT m FROM museums m WHERE m.name = :name")
    public Optional<museums> findByName(@Param("name") String name);

    @Query("SELECT m FROM museums m WHERE m.id = :id")
    public Optional<museums> findById(@Param("id") Long id);
}