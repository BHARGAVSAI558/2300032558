package com.vehicle.model.dto;

import com.vehicle.model.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BulkNotificationRequest {

    @NotNull
    private NotificationType type;

    @NotBlank
    private String message;
}
