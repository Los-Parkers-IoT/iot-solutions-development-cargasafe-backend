package Proyect.IoTParkers.trip.domain.model.aggregates;

import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import Proyect.IoTParkers.trip.domain.model.commands.CreateTripCommand;
import Proyect.IoTParkers.trip.domain.model.entities.DeliveryOrder;
import Proyect.IoTParkers.trip.domain.model.valueobjects.DeliveryOrderStatus;
import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Trip extends AuditableAbstractAggregateRoot<Trip> {
    private Long merchantId;
    private Long driverId;
    private Long deviceId;
    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryOrder> deliveryOrderList = new ArrayList<>();


    public Trip(CreateTripCommand command) {
        this.merchantId = command.merchantId();
        this.driverId = command.driverId();
        this.deviceId = command.deviceId();
        this.vehicleId = command.vehicleId();
        this.status = TripStatus.CREATED;
        command.deliveryOrderList().forEach(o -> o.setTrip(this));
        this.deliveryOrderList = command.deliveryOrderList();
    }


    public void addDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrderList.add(deliveryOrder);
        deliveryOrder.setTrip(this);
    }

    public void removeDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrderList.remove(deliveryOrder);
        deliveryOrder.setTrip(null);
    }

    public void startTrip() {
        this.status = TripStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();

        this.deliveryOrderList.forEach(o -> o.setStatus(DeliveryOrderStatus.IN_PROGRESS));
    }

    public boolean canCompleteTrip() {
        var noneMatchWithInProgress = this.deliveryOrderList.stream().noneMatch(o -> o.getStatus() == DeliveryOrderStatus.IN_PROGRESS);

        System.out.println("noneMatchWithInProgress: " + noneMatchWithInProgress);

        return this.status == TripStatus.IN_PROGRESS && noneMatchWithInProgress;
    }

    public void completeTrip() {
        if (!canCompleteTrip())
            return;

        System.out.println("Completing trip...");

        this.status = TripStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }
}
