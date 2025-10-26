package Proyect.IoTParkers.fleetmanagement.domain.model.queries;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetVehicleByPlateQuery(String plate) {
    public GetVehicleByPlateQuery {
        plate = Preconditions.requireNonBlank(plate, "Plate");
    }
}
