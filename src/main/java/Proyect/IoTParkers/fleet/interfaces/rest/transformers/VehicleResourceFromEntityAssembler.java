package Proyect.IoTParkers.fleet.interfaces.rest.transformers;

import Proyect.IoTParkers.fleet.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleet.interfaces.rest.resources.VehicleResource;

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
