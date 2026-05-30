package com.vehicle.service;

import com.vehicle.model.dto.BulkNotificationRequest;

public interface BulkNotificationService {

    void sendBulkNotifications(BulkNotificationRequest request);
}
