package com.vehicle.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VehicleTaskDto {

    @JsonProperty("TaskID")
    private String taskId;

    @JsonProperty("Duration")
    private int duration;

    @JsonProperty("Impact")
    private int impact;
}
