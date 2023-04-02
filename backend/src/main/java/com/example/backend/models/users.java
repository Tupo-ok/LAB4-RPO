package com.example.backend.models;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name="users")
@Access(AccessType.FIELD)
public class users {

    public users() { }
    public users(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "login", nullable = false, unique = true)
    public String login;

    @JsonIgnore
    @Column(name = "password")
    public String password;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @Column(name = "salt")
    public String salt;

    @Column(name = "token")
    public String token;

    @Column(name = "activity")
    public String activity;

    @ManyToMany(mappedBy = "users")
    public Set<museums> museums = new HashSet<>();

    public String getLogin(){
        return this.login;
    }

    public String getEmail(){
        return this.email;
    }

    public void addMuseum(museums m) {
        this.museums.add(m);
        m.users.add(this);
    }

    public void removeMuseum(museums m) {
        this.museums.remove(m);
        m.users.remove(this);
    }

}