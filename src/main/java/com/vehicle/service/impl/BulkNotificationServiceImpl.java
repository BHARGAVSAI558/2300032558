package com.vehicle.service.impl;

import com.vehicle.model.dto.BulkNotificationRequest;
import com.vehicle.model.entity.Student;
import com.vehicle.queue.NotificationProducer;
import com.vehicle.repository.StudentRepository;
import com.vehicle.service.BulkNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulkNotificationServiceImpl implements BulkNotificationService {

    private static final Logger log = LoggerFactory.getLogger(BulkNotificationServiceImpl.class);

    private final StudentRepository studentRepository;
    private final NotificationProducer notificationProducer;

    public BulkNotificationServiceImpl(StudentRepository studentRepository,
                                        NotificationProducer notificationProducer) {
        this.studentRepository = studentRepository;
        this.notificationProducer = notificationProducer;
    }

    @Override
    @Async
    public void sendBulkNotifications(BulkNotificationRequest request) {
        List<Student> students = studentRepository.findAll();
        log.info("Starting bulk notification for {} students", students.size());

        for (Student student : students) {
            notificationProducer.produce(student.getId(), request.getType(), request.getMessage());
        }

        log.info("All {} jobs enqueued for bulk notification", students.size());
    }
}
