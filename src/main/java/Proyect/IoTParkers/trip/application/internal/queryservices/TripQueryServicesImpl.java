package Proyect.IoTParkers.trip.application.internal.queryservices;

import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import Proyect.IoTParkers.trip.domain.services.TripQueryService;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TripQueryServicesImpl implements TripQueryService {

    private final TripRepository tripRepository;

    @Override
    public Optional<Trip> getById(UUID id) { return tripRepository.findById(id); }

    @Override
    public Page<Trip> getByMerchant(Long merchantId, TripStatus status, Instant from, Instant to, Pageable pageable) {
        boolean hasRange = (from != null && to != null);
        if (hasRange && status != null) {
            return tripRepository.findByMerchantIdAndStatusAndCreatedAtBetween(merchantId, status, from, to, pageable);
        } else if (hasRange) {
            return tripRepository.findByMerchantIdAndCreatedAtBetween(merchantId, from, to, pageable);
        } else if (status != null) {
            return tripRepository.findByMerchantIdAndStatus(merchantId, status, pageable);
        } else {
            return tripRepository.findByMerchantId(merchantId, pageable);
        }
    }

    @Override
    public Page<Trip> searchByDateRange(Instant from, Instant to, Long merchantId, TripStatus status, Pageable pageable) {
        if (from == null || to == null) throw new IllegalArgumentException("from/to son requeridos");
        boolean filterMerchant = merchantId != null;
        boolean filterStatus = status != null;

        if (filterMerchant && filterStatus) {
            return tripRepository.findByMerchantIdAndStatusAndCreatedAtBetween(merchantId, status, from, to, pageable);
        } else if (filterMerchant) {
            return tripRepository.findByMerchantIdAndCreatedAtBetween(merchantId, from, to, pageable);
        } else if (filterStatus) {
            return tripRepository.findByStatusAndCreatedAtBetween(status, from, to, pageable);
        } else {
            return tripRepository.findByCreatedAtBetween(from, to, pageable);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trip> getAll() {
        return tripRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

}

