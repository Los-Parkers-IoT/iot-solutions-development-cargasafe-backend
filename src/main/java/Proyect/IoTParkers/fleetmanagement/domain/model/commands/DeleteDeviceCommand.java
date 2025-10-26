package Proyect.IoTParkers.fleetmanagement.domain.model.commands;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record DeleteDeviceCommand(Long id) {
    public DeleteDeviceCommand {
        Preconditions.requireNonNull(id, "ID");
    }
}
