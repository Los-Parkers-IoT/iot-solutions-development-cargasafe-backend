package Proyect.IoTParkers.trip.domain.model.aggregates;

import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Trip extends AuditableAbstractAggregateRoot<Trip> {
    private Long merchantId;
    private Long clientId;
    private Long driverId;
    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;


    public Trip(Long merchantId) {
        this.merchantId = merchantId;
    }

}
