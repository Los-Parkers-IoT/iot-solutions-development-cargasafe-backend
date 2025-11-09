package Proyect.IoTParkers.trip.domain.services;

import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TripQueryService {
    Optional<Trip> getById(UUID id);

    Page<Trip> getByMerchant(Long merchantId, TripStatus status, Instant from, Instant to, Pageable pageable);

    Page<Trip> searchByDateRange(Instant from, Instant to, Long merchantId, TripStatus status, Pageable pageable);

    List<Trip> getAll();
}

