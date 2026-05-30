package com.vehicle.service.impl;

import com.vehicle.exception.ResourceNotFoundException;
import com.vehicle.model.dto.CreateNotificationRequest;
import com.vehicle.model.dto.NotificationResponse;
import com.vehicle.model.entity.Notification;
import com.vehicle.model.enums.NotificationType;
import com.vehicle.repository.NotificationRepository;
import com.vehicle.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Page<NotificationResponse> getAllNotifications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return notificationRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    @Cacheable(value = "unreadNotifications")
    public Page<NotificationResponse> getUnreadNotifications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return notificationRepository.findByIsRead(false, pageable).map(this::toResponse);
    }

    @Override
    @CacheEvict(value = "unreadNotifications", allEntries = true)
    public NotificationResponse markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notification.setRead(true);
        notificationRepository.save(notification);
        log.info("Notification {} marked as read", id);
        return toResponse(notification);
    }

    @Override
    public Page<NotificationResponse> getNotificationsByType(NotificationType type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return notificationRepository.findByType(type, pageable).map(this::toResponse);
    }

    @Override
    @CacheEvict(value = "unreadNotifications", allEntries = true)
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        Notification notification = new Notification();
        notification.setStudentId(request.getStudentId());
        notification.setType(request.getType());
        notification.setMessage(request.getMessage());
        notification.setRead(false);
        Notification saved = notificationRepository.save(notification);
        log.info("Notification created for student {}", request.getStudentId());
        return toResponse(saved);
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(n.getId(), n.getStudentId(), n.getType(),
                n.getMessage(), n.isRead(), n.getCreatedAt(), n.getUpdatedAt());
    }
}
