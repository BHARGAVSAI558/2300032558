package com.vehicle.client;

import com.vehicle.model.dto.AuthRequest;
import com.vehicle.model.dto.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenManager {

    private static final Logger log = LoggerFactory.getLogger(TokenManager.class);

    private static final String AUTH_URL = "http://4.224.186.213/evaluation-service/auth";

    @Value("${external.api.email}")
    private String email;

    @Value("${external.api.name}")
    private String name;

    @Value("${external.api.rollNo}")
    private String rollNo;

    @Value("${external.api.accessCode}")
    private String accessCode;

    @Value("${external.api.clientID}")
    private String clientID;

    @Value("${external.api.clientSecret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    private String cachedToken;
    private long tokenExpiresAt;

    public TokenManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getToken() {
        long now = System.currentTimeMillis() / 1000;
        if (cachedToken == null || now >= tokenExpiresAt - 60) {
            refreshToken();
        }
        return cachedToken;
    }

    private void refreshToken() {
        AuthRequest request = new AuthRequest(email, name, rollNo, accessCode, clientID, clientSecret);
        AuthResponse response = restTemplate.postForObject(AUTH_URL, request, AuthResponse.class);
        if (response != null && response.getAccessToken() != null) {
            cachedToken = response.getAccessToken();
            tokenExpiresAt = response.getExpiresIn();
            log.info("Token refreshed successfully");
        }
    }
}
