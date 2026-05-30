package com.vehicle.controller;

import com.vehicle.model.dto.BulkNotificationRequest;
import com.vehicle.model.dto.CreateNotificationRequest;
import com.vehicle.model.dto.NotificationResponse;
import com.vehicle.model.enums.NotificationType;
import com.vehicle.service.BulkNotificationService;
import com.vehicle.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final BulkNotificationService bulkNotificationService;

    public NotificationController(NotificationService notificationService,
                                   BulkNotificationService bulkNotificationService) {
        this.notificationService = notificationService;
        this.bulkNotificationService = bulkNotificationService;
    }

    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getAllNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(notificationService.getAllNotifications(page, size));
    }

    @GetMapping("/unread")
    public ResponseEntity<Page<NotificationResponse>> getUnreadNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(page, size));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Page<NotificationResponse>> getByType(
            @PathVariable NotificationType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(notificationService.getNotificationsByType(type, page, size));
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody CreateNotificationRequest request) {
        return ResponseEntity.ok(notificationService.createNotification(request));
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> sendBulkNotifications(
            @Valid @RequestBody BulkNotificationRequest request) {
        bulkNotificationService.sendBulkNotifications(request);
        return ResponseEntity.ok("Bulk notification job started");
    }
}
