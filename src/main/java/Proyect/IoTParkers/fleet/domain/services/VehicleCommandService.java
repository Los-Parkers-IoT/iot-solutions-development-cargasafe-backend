package Proyect.IoTParkers.fleet.domain.services;

import Proyect.IoTParkers.fleet.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleet.domain.model.commands.*;

import java.util.Optional;

public interface VehicleCommandService {
    Optional<Vehicle> handle(CreateVehicleCommand command);
    Optional<Vehicle> handle(UpdateVehicleCommand command);
    void handle(DeleteVehicleCommand command);
    Optional<Vehicle> handle(AssignDeviceToVehicleCommand command);
    Optional<Vehicle> handle(UnassignDeviceFromVehicleCommand command);
}
