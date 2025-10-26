package Proyect.IoTParkers.fleetmanagement.domain.model.commands;

import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.Capability;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.VehicleType;
import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

import java.util.Set;

public record CreateVehicleCommand(
        String plate,
        VehicleType type,
        Set<Capability> capabilities,
        VehicleStatus status,
        Integer odometerKm,
        String deviceImei
) {
    public CreateVehicleCommand {
        plate = Preconditions.requireNonBlank(plate, "Plate");
        Preconditions.requireNonNull(type, "VehicleType");
        Preconditions.requireNonNull(capabilities, "Capabilities");
        Preconditions.requireNonNull(odometerKm, "OdometerKm");
        
        if (capabilities.isEmpty()) {
            throw new IllegalArgumentException("Capabilities must not be empty");
        }
        
        if (odometerKm < 0) {
            throw new IllegalArgumentException("OdometerKm must be >= 0");
        }
    }
}
