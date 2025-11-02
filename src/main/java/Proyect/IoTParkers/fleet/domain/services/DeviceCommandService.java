package Proyect.IoTParkers.fleet.domain.services;

import Proyect.IoTParkers.fleet.domain.model.aggregates.Device;
import Proyect.IoTParkers.fleet.domain.model.commands.*;

import java.util.Optional;

public interface DeviceCommandService {
    Optional<Device> handle(CreateDeviceCommand command);
    Optional<Device> handle(UpdateDeviceCommand command);
    void handle(DeleteDeviceCommand command);
    Optional<Device> handle(UpdateDeviceFirmwareCommand command);
}
