package Proyect.IoTParkers.fleet.application.internal.queryservices;

import Proyect.IoTParkers.fleet.domain.model.aggregates.Device;
import Proyect.IoTParkers.fleet.domain.model.queries.*;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.Imei;
import Proyect.IoTParkers.fleet.domain.services.DeviceQueryService;
import Proyect.IoTParkers.fleet.infrastructure.persistence.jpa.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceQueryServiceImpl implements DeviceQueryService {
    
    private final DeviceRepository deviceRepository;
    
    public DeviceQueryServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    
    @Override
    public List<Device> handle(GetAllDevicesQuery query) {
        return deviceRepository.findAll();
    }
    
    @Override
    public Optional<Device> handle(GetDeviceByIdQuery query) {
        return deviceRepository.findById(query.id());
    }
    
    @Override
    public Optional<Device> handle(GetDeviceByImeiQuery query) {
        Imei imei = new Imei(query.imei());
        return deviceRepository.findByImei(imei);
    }
    
    @Override
    public List<Device> handle(GetDevicesByOnlineQuery query) {
        return deviceRepository.findAllByOnline(query.online());
    }
    
    @Override
    public List<Device> handle(GetDevicesByTypeQuery query) {
        return deviceRepository.findAllByType(query.type());
    }
}
