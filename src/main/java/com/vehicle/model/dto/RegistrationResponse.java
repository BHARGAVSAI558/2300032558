package com.vehicle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {

    private String clientId;
    private String clientSecret;
    private String accessToken;
    private String message;
}
