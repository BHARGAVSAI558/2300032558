package com.vehicle.queue;

import com.vehicle.model.entity.Notification;
import com.vehicle.repository.NotificationRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);
    private static final int MAX_RETRIES = 3;

    private final NotificationQueueService queueService;
    private final NotificationRepository notificationRepository;
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public NotificationConsumer(NotificationQueueService queueService,
                                 NotificationRepository notificationRepository) {
        this.queueService = queueService;
        this.notificationRepository = notificationRepository;
    }

    @PostConstruct
    public void startConsuming() {
        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    NotificationJob job = queueService.dequeue();
                    processJob(job);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.warn("Consumer thread interrupted");
                }
            }
        });
        log.info("Notification consumer started");
    }

    private void processJob(NotificationJob job) {
        try {
            Notification notification = new Notification();
            notification.setStudentId(job.getStudentId());
            notification.setType(job.getType());
            notification.setMessage(job.getMessage());
            notification.setRead(false);
            notificationRepository.save(notification);
            log.debug("Processed notification for student {}", job.getStudentId());
        } catch (Exception e) {
            log.error("Failed to process job for student {}: {}", job.getStudentId(), e.getMessage());
            if (job.getRetryCount() < MAX_RETRIES) {
                job.setRetryCount(job.getRetryCount() + 1);
                log.info("Retrying job for student {}, attempt {}", job.getStudentId(), job.getRetryCount());
                queueService.enqueue(job);
            } else {
                log.error("Job permanently failed for student {} after {} retries", job.getStudentId(), MAX_RETRIES);
            }
        }
    }
}
