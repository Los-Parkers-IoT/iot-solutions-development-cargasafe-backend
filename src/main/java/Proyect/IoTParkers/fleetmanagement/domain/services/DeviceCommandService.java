package Proyect.IoTParkers.fleetmanagement.domain.services;

import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Device;
import Proyect.IoTParkers.fleetmanagement.domain.model.commands.*;

import java.util.Optional;

public interface DeviceCommandService {
    Optional<Device> handle(CreateDeviceCommand command);
    Optional<Device> handle(UpdateDeviceCommand command);
    void handle(DeleteDeviceCommand command);
    Optional<Device> handle(UpdateDeviceFirmwareCommand command);
}
