package com.gympro.gymprobackend.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "rooms")

public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)private String name;
    @Column(nullable = false)private  int capacity;
    @Column(nullable = false)private String equipment;
    @Column(nullable = false)private Boolean active=true;

}
