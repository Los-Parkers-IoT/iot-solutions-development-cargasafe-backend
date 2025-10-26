package Proyect.IoTParkers.fleetmanagement.interfaces.rest.transformers;

import Proyect.IoTParkers.fleetmanagement.domain.model.commands.UpdateVehicleCommand;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.Capability;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.VehicleType;
import Proyect.IoTParkers.fleetmanagement.interfaces.rest.resources.UpdateVehicleResource;

import java.util.stream.Collectors;

public final class UpdateVehicleCommandFromResourceAssembler {
    public static UpdateVehicleCommand toCommandFromResource(Long id, UpdateVehicleResource resource) {
        return new UpdateVehicleCommand(
                id,
                resource.type() != null ? VehicleType.valueOf(resource.type()) : null,
                resource.capabilities() != null ?
                        resource.capabilities().stream()
                                .map(Capability::valueOf)
                                .collect(Collectors.toSet()) : null,
                resource.status() != null ? VehicleStatus.valueOf(resource.status()) : null,
                resource.odometerKm()
        );
    }
}
