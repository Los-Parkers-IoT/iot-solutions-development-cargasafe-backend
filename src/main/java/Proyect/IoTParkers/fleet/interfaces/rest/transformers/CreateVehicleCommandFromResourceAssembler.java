package Proyect.IoTParkers.fleet.interfaces.rest.transformers;

import Proyect.IoTParkers.fleet.domain.model.commands.CreateVehicleCommand;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.Capability;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleType;
import Proyect.IoTParkers.fleet.interfaces.rest.resources.CreateVehicleResource;

import java.util.stream.Collectors;

public final class CreateVehicleCommandFromResourceAssembler {
    public static CreateVehicleCommand toCommandFromResource(CreateVehicleResource resource) {
        return new CreateVehicleCommand(
                resource.plate(),
                resource.type() != null ? VehicleType.valueOf(resource.type()) : null,
                resource.capabilities() != null ? 
                        resource.capabilities().stream()
                                .map(Capability::valueOf)
                                .collect(Collectors.toSet()) : null,
                resource.status() != null ? VehicleStatus.valueOf(resource.status()) : null,
                resource.odometerKm());
    }
}
