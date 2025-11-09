package Proyect.IoTParkers.trip.interfaces.rest.resources;

import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;

import java.time.LocalDateTime;

public record TripResource(
        Long id,
        Long merchantId,
        Long clientId,
        Long driverId,
        Long vehicleId,
        TripStatus status,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime completedAt
) {
}
