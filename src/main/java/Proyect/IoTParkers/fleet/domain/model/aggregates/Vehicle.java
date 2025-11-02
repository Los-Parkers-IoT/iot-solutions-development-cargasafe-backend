package Proyect.IoTParkers.fleet.domain.model.aggregates;

import Proyect.IoTParkers.fleet.domain.model.commands.CreateVehicleCommand;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.*;
import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
public class Vehicle extends AuditableAbstractAggregateRoot<Vehicle> {
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "plate", unique = true, nullable = false))
    private final Plate plate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vehicle_capabilities", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "capability")
    private Set<Capability> capabilities;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "odometer_km", nullable = false))
    private OdometerKm odometerKm;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "device_imei"))
    private Imei deviceImei;
    
    public Vehicle() {
        this.plate = null;
        this.type = null;
        this.capabilities = new HashSet<>();
        this.status = VehicleStatus.IN_SERVICE;
        this.odometerKm = null;
        this.deviceImei = null;
    }
    
    public Vehicle(CreateVehicleCommand command) {
        this.plate = new Plate(command.plate());
        this.type = command.type();
        this.capabilities = new HashSet<>(command.capabilities());
        this.status = command.status() != null ? command.status() : VehicleStatus.IN_SERVICE;
        this.odometerKm = new OdometerKm(command.odometerKm());
        this.deviceImei = null;
    }
    
    public void updateType(VehicleType type) {
        this.type = type;
    }
    
    public void updateCapabilities(Set<Capability> capabilities) {
        if (capabilities != null && !capabilities.isEmpty()) {
            this.capabilities = new HashSet<>(capabilities);
        }
    }
    
    public void updateStatus(VehicleStatus status) {
        if (this.status == VehicleStatus.RETIRED && status != VehicleStatus.RETIRED) {
            throw new IllegalStateException("Cannot change status from RETIRED to another state");
        }
        this.status = status;
    }
    
    public void updateOdometer(Integer newOdometerKm) {
        OdometerKm newOdometer = new OdometerKm(newOdometerKm);
        if (this.odometerKm != null && newOdometer.value() < this.odometerKm.value()) {
            throw new IllegalArgumentException("Odometer value cannot decrease. Current: " + this.odometerKm.value() + ", New: " + newOdometer.value());
        }
        this.odometerKm = newOdometer;
    }
    
    public void assignDevice(Imei deviceImei) {
        if (this.status == VehicleStatus.RETIRED) {
            throw new IllegalStateException("Cannot assign device to RETIRED vehicle");
        }
        if (this.deviceImei != null) {
            throw new IllegalStateException("Vehicle already has a device assigned");
        }
        this.deviceImei = deviceImei;
    }
    
    public void unassignDevice() {
        this.deviceImei = null;
    }
    
    public boolean hasDevice() {
        return this.deviceImei != null;
    }
}
