package Proyect.IoTParkers.trip.domain.model.entities;

import Proyect.IoTParkers.trip.domain.model.valueobjects.DeliveryOrderStatus;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "delivery_orders")
public class DeliveryOrder {

    @Id
    @Column(name="id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(name="external_order_id", nullable = false)
    private UUID externalOrderId;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private DeliveryOrderStatus status = DeliveryOrderStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_id", nullable = false)
    private Proyect.IoTParkers.trip.domain.model.aggregates.Trip trip;

    @Column(name="created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected DeliveryOrder() { }

    public DeliveryOrder(UUID externalOrderId, Proyect.IoTParkers.trip.domain.model.aggregates.Trip trip) {
        this.externalOrderId = externalOrderId;
        this.trip = trip;
    }

    //getters
    public UUID getId() {return id;}
    public UUID getExternalOrderId() {return externalOrderId;}
    public DeliveryOrderStatus getStatus() {return status;}
    public Proyect.IoTParkers.trip.domain.model.aggregates.Trip getTrip() {return trip;}
    public Instant getCreatedAt() {return createdAt;}


}
