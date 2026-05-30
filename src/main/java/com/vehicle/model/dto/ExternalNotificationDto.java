package com.vehicle.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExternalNotificationDto {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Timestamp")
    private String timestamp;
}
