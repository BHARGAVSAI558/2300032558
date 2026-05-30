package com.vehicle.client;

import com.vehicle.model.dto.NotificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class ExternalNotificationClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalNotificationClient.class);
    private static final String EXTERNAL_API_URL = "http://localhost:8080/evaluation-service/notifications";

    private final RestTemplate restTemplate;

    public ExternalNotificationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<NotificationResponse> fetchExternalNotifications() {
        try {
            ResponseEntity<List<NotificationResponse>> response = restTemplate.exchange(
                    EXTERNAL_API_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (RestClientException e) {
            log.error("Failed to fetch external notifications: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
