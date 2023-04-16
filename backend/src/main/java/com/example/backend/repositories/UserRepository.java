package com.example.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.example.backend.models.users;

public interface UserRepository extends CrudRepository<users, Long> {
    @Query("SELECT u FROM users u WHERE u.login = :login")
    public Optional<users> findByName(@Param("login") String login);

    @Query("SELECT u FROM users u WHERE u.email = :email")
    public Optional<users> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM users u WHERE u.id = :id")
    public Optional<users> findById(@Param("id") Long id);
	
	@Query("SELECT u FROM users u WHERE u.token = :userToken")
    public Optional<users> findByToken(@Param("userToken") String userToken);

    @Query("SELECT u FROM users u WHERE u.login = :login")
    public Optional<users> findByLogin(@Param("login") String login); 
}
