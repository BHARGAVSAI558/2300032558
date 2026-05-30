package com.vehicle.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vehicle.model.dto.DepotDto;
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
public class DepotClient {

    private static final Logger log = LoggerFactory.getLogger(DepotClient.class);
    private static final String URL = "http://4.224.186.213/evaluation-service/depots";

    private final RestTemplate restTemplate;
    private final TokenManager tokenManager;

    public DepotClient(RestTemplate restTemplate, TokenManager tokenManager) {
        this.restTemplate = restTemplate;
        this.tokenManager = tokenManager;
    }

    public List<DepotDto> fetchDepots() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(tokenManager.getToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<DepotsWrapper> response = restTemplate.exchange(
                    URL, HttpMethod.GET, entity, DepotsWrapper.class
            );
            if (response.getBody() != null && response.getBody().getDepots() != null) {
                return response.getBody().getDepots();
            }
            return Collections.emptyList();
        } catch (RestClientException e) {
            log.error("Failed to fetch depots: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Data
    static class DepotsWrapper {
        @JsonProperty("depots")
        private List<DepotDto> depots;
    }
}
