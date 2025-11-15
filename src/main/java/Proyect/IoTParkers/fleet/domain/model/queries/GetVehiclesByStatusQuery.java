package Proyect.IoTParkers.fleet.domain.model.queries;

import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetVehiclesByStatusQuery(VehicleStatus status) {
    public GetVehiclesByStatusQuery {
        Preconditions.requireNonNull(status, "VehicleStatus");
    }
}
