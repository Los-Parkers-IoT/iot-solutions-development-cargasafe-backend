package Proyect.IoTParkers.fleetmanagement.domain.services;

import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleetmanagement.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface VehicleQueryService {
    List<Vehicle> handle(GetAllVehiclesQuery query);
    Optional<Vehicle> handle(GetVehicleByIdQuery query);
    Optional<Vehicle> handle(GetVehicleByPlateQuery query);
    List<Vehicle> handle(GetVehiclesByStatusQuery query);
    List<Vehicle> handle(GetVehiclesByTypeQuery query);
}
