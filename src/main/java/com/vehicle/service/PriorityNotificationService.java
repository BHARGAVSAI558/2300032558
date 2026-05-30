package com.vehicle.service;

import com.vehicle.model.dto.PriorityNotificationResponse;

import java.util.List;

public interface PriorityNotificationService {

    List<PriorityNotificationResponse> getPriorityNotifications(int limit);
}
