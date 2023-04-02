package com.example.backend.models;

import jakarta.persistence.*;


@Entity
@Table(name = "artists")
@Access(AccessType.FIELD)
public class artists {

    public artists() { }
    public artists(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @Column(name = "age", nullable = false)
    public int age;

    @ManyToOne()
    @JoinColumn(name = "country_id")
    public countries country;

    public String getName(){
        return this.name;
    }
}