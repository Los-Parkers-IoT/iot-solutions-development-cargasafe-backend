package Proyect.IoTParkers.fleet.domain.model.commands;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record AssignDeviceToVehicleCommand(Long vehicleId, String deviceImei) {
    public AssignDeviceToVehicleCommand {
        Preconditions.requireNonNull(vehicleId, "VehicleId");
        deviceImei = Preconditions.requireNonBlank(deviceImei, "DeviceImei");
    }
}
