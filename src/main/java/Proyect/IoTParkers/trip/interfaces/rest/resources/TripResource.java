package Proyect.IoTParkers.trip.interfaces.rest.resources;

import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import java.time.Instant;
import java.util.UUID;

public record TripResource(
    UUID id,
    Long merchantId,
    UUID clientId,
    UUID driverId,
    UUID vehicleId,
    TripStatus status,
    Instant createdAt,
    Instant startedAt,
    Instant completedAt
) { }
