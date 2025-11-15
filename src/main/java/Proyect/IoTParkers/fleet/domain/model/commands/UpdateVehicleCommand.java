package Proyect.IoTParkers.fleet.domain.model.commands;

import Proyect.IoTParkers.fleet.domain.model.valueobjects.Capability;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleType;
import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

import java.util.Set;

public record UpdateVehicleCommand(
        Long id,
        VehicleType type,
        Set<Capability> capabilities,
        VehicleStatus status,
        Integer odometerKm
) {
    public UpdateVehicleCommand {
        Preconditions.requireNonNull(id, "ID");
    }
}
