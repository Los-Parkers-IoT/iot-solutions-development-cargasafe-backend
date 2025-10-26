package Proyect.IoTParkers.fleetmanagement.domain.model.commands;

import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.DeviceType;
import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record CreateDeviceCommand(
        String imei,
        DeviceType type,
        String firmware,
        Boolean online
) {
    public CreateDeviceCommand {
        imei = Preconditions.requireNonBlank(imei, "IMEI");
        Preconditions.requireNonNull(type, "DeviceType");
        firmware = Preconditions.requireNonBlank(firmware, "Firmware");
    }
}
