package Proyect.IoTParkers.fleetmanagement.interfaces.rest.transformers;

import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleetmanagement.interfaces.rest.resources.VehicleResource;

import java.util.stream.Collectors;

public final class VehicleResourceFromEntityAssembler {
    public static VehicleResource toResourceFromEntity(Vehicle entity) {
        return new VehicleResource(
                entity.getId(),
                entity.getPlate().value(),
                entity.getType().name(),
                entity.getCapabilities().stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet()),
                entity.getStatus().name(),
                entity.getOdometerKm().value(),
                entity.getDeviceImei() != null ? entity.getDeviceImei().value() : null
        );
    }
}
