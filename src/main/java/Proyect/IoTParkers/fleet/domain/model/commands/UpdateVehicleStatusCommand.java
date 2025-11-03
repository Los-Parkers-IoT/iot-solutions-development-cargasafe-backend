package Proyect.IoTParkers.fleet.domain.model.commands;

import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleStatus;

public record UpdateVehicleStatusCommand(Long vehicleId, VehicleStatus status) {
    public UpdateVehicleStatusCommand {
        if (vehicleId == null) {
            throw new IllegalArgumentException("vehicleId is required");
        }
        if (status == null) {
            throw new IllegalArgumentException("status is required");
        }
    }
}