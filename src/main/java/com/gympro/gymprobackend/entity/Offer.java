package com.gympro.gymprobackend.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "offers")

public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private double price;
    @Column(nullable = false) private int durationDays;
    @Column(nullable = false) private int  sessionsIncluded;
    private boolean active = true;
}
