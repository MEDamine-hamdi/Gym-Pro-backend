package com.gympro.gymprobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class LoginRequest {
    private String email;
    private String password;
}



