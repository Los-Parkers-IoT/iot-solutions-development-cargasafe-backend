package Proyect.IoTParkers.fleetmanagement.domain.services;

import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleetmanagement.domain.model.commands.*;

import java.util.Optional;

public interface VehicleCommandService {
    Optional<Vehicle> handle(CreateVehicleCommand command);
    Optional<Vehicle> handle(UpdateVehicleCommand command);
    void handle(DeleteVehicleCommand command);
    Optional<Vehicle> handle(AssignDeviceToVehicleCommand command);
    Optional<Vehicle> handle(UnassignDeviceFromVehicleCommand command);
}
