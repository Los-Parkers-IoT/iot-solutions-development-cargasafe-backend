package Proyect.IoTParkers.trip.interfaces.rest.resources;

import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import java.time.Instant;

public record UpdateTripStatusResource(TripStatus status, Instant startedAt, Instant completedAt) {}
