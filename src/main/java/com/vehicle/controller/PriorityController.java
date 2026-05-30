package com.vehicle.controller;

import com.vehicle.model.dto.PriorityNotificationResponse;
import com.vehicle.service.PriorityNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class PriorityController {

    private final PriorityNotificationService priorityNotificationService;

    public PriorityController(PriorityNotificationService priorityNotificationService) {
        this.priorityNotificationService = priorityNotificationService;
    }

    @GetMapping("/priority")
    public ResponseEntity<List<PriorityNotificationResponse>> getPriorityNotifications(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(priorityNotificationService.getPriorityNotifications(limit));
    }
}
