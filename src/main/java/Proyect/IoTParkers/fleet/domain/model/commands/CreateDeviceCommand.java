package Proyect.IoTParkers.fleet.domain.model.commands;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record CreateDeviceCommand(
        String imei,
        String firmware,
        Boolean online
) {
    public CreateDeviceCommand {
        imei = Preconditions.requireNonBlank(imei, "IMEI");
        firmware = Preconditions.requireNonBlank(firmware, "Firmware");
    }
}
