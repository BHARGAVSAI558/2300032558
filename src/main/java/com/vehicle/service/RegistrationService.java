package com.vehicle.service;

import com.vehicle.model.dto.RegistrationRequest;
import com.vehicle.model.dto.RegistrationResponse;

public interface RegistrationService {

    RegistrationResponse register(RegistrationRequest request);
}
