package Proyect.IoTParkers.trip.domain.services;

import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface TripCommandService {

    Trip create(Long merchantId, Instant createdAtOverride);

    Optional<Trip> updateStatus(UUID tripId, TripStatus status, Instant startedAtOverride, Instant completedAtOverride);
}
