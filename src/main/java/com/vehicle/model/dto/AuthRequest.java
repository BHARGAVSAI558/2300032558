package com.vehicle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {

    private String email;
    private String name;
    private String rollNo;
    private String accessCode;
    private String clientID;
    private String clientSecret;
}
