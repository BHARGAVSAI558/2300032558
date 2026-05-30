package com.vehicle.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String mobileNo;

    @NotBlank
    private String githubUsername;

    @NotBlank
    private String rollNo;

    @NotBlank
    private String accessCode;
}
