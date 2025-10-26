package Proyect.IoTParkers.fleetmanagement.domain.model.commands;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record UnassignDeviceFromVehicleCommand(Long vehicleId) {
    public UnassignDeviceFromVehicleCommand {
        Preconditions.requireNonNull(vehicleId, "VehicleId");
    }
}
