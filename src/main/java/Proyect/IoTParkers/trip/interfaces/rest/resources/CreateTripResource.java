package Proyect.IoTParkers.trip.interfaces.rest.resources;

import java.time.Instant;

public record CreateTripResource(Long merchantId, Instant createdAt) {}
