package Proyect.IoTParkers.fleetmanagement.domain.model.aggregates;

import Proyect.IoTParkers.fleetmanagement.domain.model.commands.CreateDeviceCommand;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.*;
import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Device extends AuditableAbstractAggregateRoot<Device> {
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "imei", unique = true, nullable = false))
    private final Imei imei;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType type;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "firmware", nullable = false))
    private FirmwareVersion firmware;
    
    @Column(nullable = false)
    private boolean online;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "vehicle_plate"))
    private Plate vehiclePlate;
    
    public Device() {
        this.imei = null;
        this.type = null;
        this.firmware = null;
        this.online = false;
        this.vehiclePlate = null;
    }
    
    public Device(CreateDeviceCommand command) {
        this.imei = new Imei(command.imei());
        this.type = command.type();
        this.firmware = new FirmwareVersion(command.firmware());
        this.online = command.online() != null ? command.online() : false;
        this.vehiclePlate = command.vehiclePlate() != null ? new Plate(command.vehiclePlate()) : null;
    }
    
    public void updateType(DeviceType type) {
        this.type = type;
    }
    
    public void updateFirmware(FirmwareVersion firmware) {
        this.firmware = firmware;
    }
    
    public void updateOnline(boolean online) {
        this.online = online;
    }
    
    public void assignToVehicle(Plate vehiclePlate) {
        if (this.vehiclePlate != null) {
            throw new IllegalStateException("Device is already assigned to a vehicle");
        }
        this.vehiclePlate = vehiclePlate;
    }
    
    public void unassignFromVehicle() {
        this.vehiclePlate = null;
    }
    
    public boolean isAssigned() {
        return this.vehiclePlate != null;
    }
}
