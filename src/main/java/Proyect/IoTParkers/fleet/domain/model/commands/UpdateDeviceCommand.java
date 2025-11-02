package Proyect.IoTParkers.fleet.domain.model.commands;

import Proyect.IoTParkers.fleet.domain.model.valueobjects.DeviceType;
import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record UpdateDeviceCommand(
        Long id,
        DeviceType type,
        String firmware,
        Boolean online
) {
    public UpdateDeviceCommand {
        Preconditions.requireNonNull(id, "ID");
    }
}
