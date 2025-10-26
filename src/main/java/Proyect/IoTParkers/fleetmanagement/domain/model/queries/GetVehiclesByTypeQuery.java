package Proyect.IoTParkers.fleetmanagement.domain.model.queries;

import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.VehicleType;
import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetVehiclesByTypeQuery(VehicleType type) {
    public GetVehiclesByTypeQuery {
        Preconditions.requireNonNull(type, "VehicleType");
    }
}
