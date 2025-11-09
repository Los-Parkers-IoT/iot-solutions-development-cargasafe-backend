package Proyect.IoTParkers.trip.domain.model.entities;

import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.valueobjects.DeliveryOrderStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class DeliveryOrder extends AuditableModel {
    private Long externalOrderId;

    @Enumerated(EnumType.STRING)
    private DeliveryOrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    protected DeliveryOrder() {
    }

    public DeliveryOrder(Long externalOrderId, Trip trip) {
        this.externalOrderId = externalOrderId;
        this.trip = trip;
    }
}
