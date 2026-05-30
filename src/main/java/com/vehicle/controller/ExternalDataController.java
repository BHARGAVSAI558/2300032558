package com.vehicle.controller;

import com.vehicle.client.DepotClient;
import com.vehicle.client.ExternalNotificationClient;
import com.vehicle.client.VehicleClient;
import com.vehicle.model.dto.DepotDto;
import com.vehicle.model.dto.ExternalNotificationDto;
import com.vehicle.model.dto.VehicleTaskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/external")
public class ExternalDataController {

    private final ExternalNotificationClient notificationClient;
    private final DepotClient depotClient;
    private final VehicleClient vehicleClient;

    public ExternalDataController(ExternalNotificationClient notificationClient,
                                   DepotClient depotClient,
                                   VehicleClient vehicleClient) {
        this.notificationClient = notificationClient;
        this.depotClient = depotClient;
        this.vehicleClient = vehicleClient;
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<ExternalNotificationDto>> getExternalNotifications() {
        return ResponseEntity.ok(notificationClient.fetchNotifications());
    }

    @GetMapping("/depots")
    public ResponseEntity<List<DepotDto>> getDepots() {
        return ResponseEntity.ok(depotClient.fetchDepots());
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleTaskDto>> getVehicles() {
        return ResponseEntity.ok(vehicleClient.fetchVehicles());
    }
}
