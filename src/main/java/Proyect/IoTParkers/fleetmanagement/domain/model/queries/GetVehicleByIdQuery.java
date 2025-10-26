package Proyect.IoTParkers.fleetmanagement.domain.model.queries;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetVehicleByIdQuery(Long id) {
    public GetVehicleByIdQuery {
        Preconditions.requireNonNull(id, "ID");
    }
}
