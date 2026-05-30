package com.vehicle.service.impl;

import com.vehicle.model.dto.PriorityNotificationResponse;
import com.vehicle.model.entity.Notification;
import com.vehicle.model.enums.NotificationType;
import com.vehicle.repository.NotificationRepository;
import com.vehicle.service.PriorityNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class PriorityNotificationServiceImpl implements PriorityNotificationService {

    private static final Logger log = LoggerFactory.getLogger(PriorityNotificationServiceImpl.class);

    private static final int PLACEMENT_WEIGHT = 10;
    private static final int RESULT_WEIGHT = 8;
    private static final int EVENT_WEIGHT = 5;
    private static final int UNREAD_WEIGHT = 5;
    private static final int RECENT_WEIGHT = 3;

    private final NotificationRepository notificationRepository;

    public PriorityNotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<PriorityNotificationResponse> getPriorityNotifications(int limit) {
        List<Notification> all = notificationRepository.findAll();

        PriorityQueue<PriorityNotificationResponse> pq = new PriorityQueue<>(
                Comparator.comparingInt(PriorityNotificationResponse::getPriorityScore).reversed()
        );

        for (Notification n : all) {
            int score = calculateScore(n);
            pq.offer(new PriorityNotificationResponse(
                    n.getId(), n.getStudentId(), n.getType(),
                    n.getMessage(), n.isRead(), n.getCreatedAt(), score
            ));
        }

        List<PriorityNotificationResponse> result = new java.util.ArrayList<>();
        int count = 0;
        while (!pq.isEmpty() && count < limit) {
            result.add(pq.poll());
            count++;
        }

        log.info("Returning {} priority notifications", result.size());
        return result;
    }

    private int calculateScore(Notification n) {
        int score = 0;

        if (n.getType() == NotificationType.PLACEMENT) {
            score += PLACEMENT_WEIGHT;
        } else if (n.getType() == NotificationType.RESULT) {
            score += RESULT_WEIGHT;
        } else if (n.getType() == NotificationType.EVENT) {
            score += EVENT_WEIGHT;
        }

        if (!n.isRead()) {
            score += UNREAD_WEIGHT;
        }

        if (n.getCreatedAt() != null &&
                n.getCreatedAt().isAfter(LocalDateTime.now().minusHours(24))) {
            score += RECENT_WEIGHT;
        }

        return score;
    }
}
