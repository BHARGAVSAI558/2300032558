package com.vehicle.service;

import com.vehicle.model.dto.CreateNotificationRequest;
import com.vehicle.model.dto.NotificationResponse;
import com.vehicle.model.enums.NotificationType;
import org.springframework.data.domain.Page;

public interface NotificationService {

    Page<NotificationResponse> getAllNotifications(int page, int size);

    Page<NotificationResponse> getUnreadNotifications(int page, int size);

    NotificationResponse markAsRead(Long id);

    Page<NotificationResponse> getNotificationsByType(NotificationType type, int page, int size);

    NotificationResponse createNotification(CreateNotificationRequest request);
}
