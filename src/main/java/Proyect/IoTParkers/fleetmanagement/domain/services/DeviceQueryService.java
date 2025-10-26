package Proyect.IoTParkers.fleetmanagement.domain.services;

import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Device;
import Proyect.IoTParkers.fleetmanagement.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface DeviceQueryService {
    List<Device> handle(GetAllDevicesQuery query);
    Optional<Device> handle(GetDeviceByIdQuery query);
    Optional<Device> handle(GetDeviceByImeiQuery query);
    List<Device> handle(GetDevicesByOnlineQuery query);
    List<Device> handle(GetDevicesByTypeQuery query);
}
