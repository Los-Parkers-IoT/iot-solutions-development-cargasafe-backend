package Proyect.IoTParkers.trip.domain.model.entities;

import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.valueobjects.DeliveryOrderStatus;
import Proyect.IoTParkers.trip.domain.model.valueobjects.Location;
import Proyect.IoTParkers.trip.domain.model.valueobjects.OrderThresholds;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Entity
public class DeliveryOrder extends AuditableModel {
    private String clientEmail;
    private Long sequenceOrder;
    private LocalDateTime arrivalAt;

    @Embedded
    private OrderThresholds orderThresholds;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private DeliveryOrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public DeliveryOrder() {
        this.status = DeliveryOrderStatus.PENDING;
    }

    public void markAsDelivered() {
        this.status = DeliveryOrderStatus.DELIVERED;
        this.arrivalAt = LocalDateTime.now();
    }
}
