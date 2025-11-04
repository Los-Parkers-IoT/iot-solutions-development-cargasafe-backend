package Proyect.IoTParkers.trip.infrastructure.persistence.jpa;

import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {

    Page<Trip> findByMerchantId(Long merchantId, Pageable pageable);

    Page<Trip> findByMerchantIdAndStatus(Long merchantId, TripStatus status, Pageable pageable);

    Page<Trip> findByCreatedAtBetween(Instant from, Instant to, Pageable pageable);

    Page<Trip> findByMerchantIdAndCreatedAtBetween(Long merchantId, Instant from, Instant to, Pageable pageable);

    Page<Trip> findByMerchantIdAndStatusAndCreatedAtBetween(Long merchantId, TripStatus status, Instant from, Instant to, Pageable pageable);

    Page<Trip> findByStatusAndCreatedAtBetween(TripStatus status, Instant from, Instant to, Pageable pageable);
}
