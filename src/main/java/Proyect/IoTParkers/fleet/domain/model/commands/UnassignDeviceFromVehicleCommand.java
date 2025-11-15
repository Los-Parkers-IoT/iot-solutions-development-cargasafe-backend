package Proyect.IoTParkers.fleet.domain.model.commands;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record UnassignDeviceFromVehicleCommand(Long vehicleId, String deviceImei) {
    public UnassignDeviceFromVehicleCommand {
        Preconditions.requireNonNull(vehicleId, "VehicleId");
        Preconditions.requireNonNull(deviceImei, "DeviceImei");
    }
}
