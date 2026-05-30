package com.vehicle.queue;

import com.vehicle.model.enums.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationProducer {

    private static final Logger log = LoggerFactory.getLogger(NotificationProducer.class);

    private final NotificationQueueService queueService;

    public NotificationProducer(NotificationQueueService queueService) {
        this.queueService = queueService;
    }

    public void produce(Long studentId, NotificationType type, String message) {
        NotificationJob job = new NotificationJob(studentId, type, message, 0,
                UUID.randomUUID().toString());
        queueService.enqueue(job);
        log.debug("Produced job for student {}", studentId);
    }
}
