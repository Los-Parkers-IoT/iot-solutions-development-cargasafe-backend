package Proyect.IoTParkers.fleet.domain.services;

import Proyect.IoTParkers.fleet.domain.model.aggregates.Device;
import Proyect.IoTParkers.fleet.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface DeviceQueryService {
    List<Device> handle(GetAllDevicesQuery query);
    Optional<Device> handle(GetDeviceByIdQuery query);
    Optional<Device> handle(GetDeviceByImeiQuery query);
    List<Device> handle(GetDevicesByOnlineQuery query);
}
