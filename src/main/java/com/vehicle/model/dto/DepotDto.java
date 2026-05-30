package com.vehicle.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DepotDto {

    @JsonProperty("ID")
    private int id;

    @JsonProperty("MechanicHours")
    private int mechanicHours;
}
