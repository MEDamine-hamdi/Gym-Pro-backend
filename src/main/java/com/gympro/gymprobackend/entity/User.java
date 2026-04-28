package com.gympro.gymprobackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")

public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String userName;
    @Column(nullable = false)private String email;
    @Column(nullable = false)private String passWord;

}
