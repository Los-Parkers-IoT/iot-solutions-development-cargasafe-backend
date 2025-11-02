package Proyect.IoTParkers.fleet.domain.model.commands;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record DeleteVehicleCommand(Long id) {
    public DeleteVehicleCommand {
        Preconditions.requireNonNull(id, "ID");
    }
}
