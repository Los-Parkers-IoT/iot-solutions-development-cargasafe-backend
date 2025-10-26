package Proyect.IoTParkers.fleetmanagement.domain.model.commands;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record UpdateDeviceFirmwareCommand(Long id, String firmware) {
    public UpdateDeviceFirmwareCommand {
        Preconditions.requireNonNull(id, "ID");
        firmware = Preconditions.requireNonBlank(firmware, "Firmware");
    }
}
