package com.vehicle.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;

@Service
public class NotificationQueueService {

    private static final Logger log = LoggerFactory.getLogger(NotificationQueueService.class);

    private final LinkedBlockingQueue<NotificationJob> queue = new LinkedBlockingQueue<>();

    public void enqueue(NotificationJob job) {
        queue.offer(job);
        log.debug("Job enqueued for student {}", job.getStudentId());
    }

    public NotificationJob dequeue() throws InterruptedException {
        return queue.take();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
