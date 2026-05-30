package com.vehicle.queue;

import com.vehicle.model.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationJob {

    private Long studentId;
    private NotificationType type;
    private String message;
    private int retryCount;
    private String jobId;
}
