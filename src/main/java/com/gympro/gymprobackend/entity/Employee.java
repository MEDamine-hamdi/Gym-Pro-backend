package com.gympro.gymprobackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String firstName;
    @Column(nullable = false) private String lastName;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeType employeeType;  // COACH, RECEPTIONIST, CLEANING, MAINTENANCE

    private String specialty;
    private BigDecimal salary;

    @Column(nullable = false)
    private LocalDate hireDate;

    private boolean active = true;
}


