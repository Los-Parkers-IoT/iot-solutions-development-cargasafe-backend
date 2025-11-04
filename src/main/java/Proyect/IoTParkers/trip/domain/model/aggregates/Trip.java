package Proyect.IoTParkers.trip.domain.model.aggregates;

import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "merchant_id", nullable = false)
    private Long merchantId;

    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "driver_id")
    private UUID driverId;

    @Column(name = "vehicle_id")
    private UUID vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TripStatus status = TripStatus.CREATED;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;


    protected Trip() { }

    public Trip(Long merchantId) {
        this.merchantId = merchantId;
    }

    public UUID getId() { return id; }
    public Long getMerchantId() { return merchantId; }
    public UUID getClientId() { return clientId; }
    public UUID getDriverId() { return driverId; }
    public UUID getVehicleId() { return vehicleId; }
    public TripStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getCompletedAt() { return completedAt; }
}
