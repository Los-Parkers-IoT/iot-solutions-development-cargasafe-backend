package Proyect.IoTParkers.fleetmanagement.application.internal.commandservices;

import Proyect.IoTParkers.fleetmanagement.domain.exceptions.*;
import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Device;
import Proyect.IoTParkers.fleetmanagement.domain.model.commands.*;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.FirmwareVersion;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.Imei;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.Plate;
import Proyect.IoTParkers.fleetmanagement.domain.services.DeviceCommandService;
import Proyect.IoTParkers.fleetmanagement.infrastructure.persistence.jpa.DeviceRepository;
import Proyect.IoTParkers.fleetmanagement.infrastructure.persistence.jpa.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {
    
    private final DeviceRepository deviceRepository;
    private final VehicleRepository vehicleRepository;
    
    public DeviceCommandServiceImpl(DeviceRepository deviceRepository, VehicleRepository vehicleRepository) {
        this.deviceRepository = deviceRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    @Override
    public Optional<Device> handle(CreateDeviceCommand command) {
        var imei = new Imei(command.imei());
        if (deviceRepository.existsByImei(imei)) {
            throw new DeviceImeiAlreadyExistsException(command.imei());
        }
        var device = new Device(command);
        return Optional.of(deviceRepository.save(device));
    }
    
    @Override
    @Transactional
    public Optional<Device> handle(UpdateDeviceCommand command) {
        var device = deviceRepository.findById(command.id())
                .orElseThrow(() -> new DeviceNotFoundException(command.id()));
        
        if (command.type() != null) {
            device.updateType(command.type());
        }
        
        if (command.firmware() != null && !command.firmware().isBlank()) {
            FirmwareVersion firmware = new FirmwareVersion(command.firmware());
            device.updateFirmware(firmware);
        }
        
        if (command.online() != null) {
            device.updateOnline(command.online());
        }
        
        return Optional.of(deviceRepository.save(device));
    }
    
    @Override
    @Transactional
    public void handle(DeleteDeviceCommand command) {
        var device = deviceRepository.findById(command.id())
                .orElseThrow(() -> new DeviceNotFoundException(command.id()));
        
        // Restricción: solo se permite eliminar devices no asignados
        if (device.isAssigned()) {
            throw new DeviceAssignmentConflictException("Cannot delete device assigned to a vehicle. Unassign device first.");
        }
        
        deviceRepository.delete(device);
    }
    
    @Override
    @Transactional
    public Optional<Device> handle(UpdateDeviceFirmwareCommand command) {
        var device = deviceRepository.findById(command.id())
                .orElseThrow(() -> new DeviceNotFoundException(command.id()));
        
        FirmwareVersion firmware = new FirmwareVersion(command.firmware());
        device.updateFirmware(firmware);
        
        return Optional.of(deviceRepository.save(device));
    }
}
