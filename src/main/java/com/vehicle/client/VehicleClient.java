package com.vehicle.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vehicle.model.dto.VehicleTaskDto;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class VehicleClient {

    private static final Logger log = LoggerFactory.getLogger(VehicleClient.class);
    private static final String URL = "http://4.224.186.213/evaluation-service/vehicles";

    private final RestTemplate restTemplate;
    private final TokenManager tokenManager;

    public VehicleClient(RestTemplate restTemplate, TokenManager tokenManager) {
        this.restTemplate = restTemplate;
        this.tokenManager = tokenManager;
    }

    public List<VehicleTaskDto> fetchVehicles() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(tokenManager.getToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<VehiclesWrapper> response = restTemplate.exchange(
                    URL, HttpMethod.GET, entity, VehiclesWrapper.class
            );
            if (response.getBody() != null && response.getBody().getVehicles() != null) {
                return response.getBody().getVehicles();
            }
            return Collections.emptyList();
        } catch (RestClientException e) {
            log.error("Failed to fetch vehicles: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Data
    static class VehiclesWrapper {
        @JsonProperty("vehicles")
        private List<VehicleTaskDto> vehicles;
    }
}
