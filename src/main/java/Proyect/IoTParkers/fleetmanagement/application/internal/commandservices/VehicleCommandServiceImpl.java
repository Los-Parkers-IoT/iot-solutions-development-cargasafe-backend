package Proyect.IoTParkers.fleetmanagement.application.internal.commandservices;

import Proyect.IoTParkers.fleetmanagement.domain.exceptions.*;
import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleetmanagement.domain.model.commands.*;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.Imei;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.Plate;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleetmanagement.domain.services.VehicleCommandService;
import Proyect.IoTParkers.fleetmanagement.infrastructure.persistence.jpa.DeviceRepository;
import Proyect.IoTParkers.fleetmanagement.infrastructure.persistence.jpa.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {
    
    private final VehicleRepository vehicleRepository;
    private final DeviceRepository deviceRepository;
    
    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository, DeviceRepository deviceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    @Override
    public Optional<Vehicle> handle(CreateVehicleCommand command) {
        var plate = new Plate(command.plate());
        if (vehicleRepository.existsByPlate(plate)) {
            throw new VehiclePlateAlreadyExistsException(command.plate());
        }
        var vehicle = new Vehicle(command);
        return Optional.of(vehicleRepository.save(vehicle));
    }
    
    @Override
    @Transactional
    public Optional<Vehicle> handle(UpdateVehicleCommand command) {
        var vehicle = vehicleRepository.findById(command.id())
                .orElseThrow(() -> new VehicleNotFoundException(command.id()));
        
        if (command.type() != null) {
            vehicle.updateType(command.type());
        }
        
        if (command.capabilities() != null && !command.capabilities().isEmpty()) {
            vehicle.updateCapabilities(command.capabilities());
        }
        
        if (command.status() != null) {
            vehicle.updateStatus(command.status());
        }
        
        if (command.odometerKm() != null) {
            vehicle.updateOdometer(command.odometerKm());
        }
        
        return Optional.of(vehicleRepository.save(vehicle));
    }
    
    @Override
    @Transactional
    public void handle(DeleteVehicleCommand command) {
        var vehicle = vehicleRepository.findById(command.id())
                .orElseThrow(() -> new VehicleNotFoundException(command.id()));
        
        // Restricción: solo se permite eliminar vehículos RETIRED sin device asignado
        if (vehicle.getStatus() != VehicleStatus.RETIRED) {
            throw new IllegalStateException("Vehicle must be in RETIRED status before deletion");
        }
        
        if (vehicle.hasDevice()) {
            throw new DeviceAssignmentConflictException("Cannot delete vehicle with assigned device. Unassign device first.");
        }
        
        vehicleRepository.delete(vehicle);
    }
    
    @Override
    @Transactional
    public Optional<Vehicle> handle(AssignDeviceToVehicleCommand command) {
        var vehicle = vehicleRepository.findById(command.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(command.vehicleId()));
        
        Imei deviceImei = new Imei(command.deviceImei());
        var device = deviceRepository.findByImei(deviceImei)
                .orElseThrow(() -> new DeviceNotFoundException(command.deviceImei()));
        
        // Validar que el vehículo no esté RETIRED
        if (vehicle.getStatus() == VehicleStatus.RETIRED) {
            throw new IllegalStateException("Cannot assign device to RETIRED vehicle");
        }
        
        // Validar que el vehículo no tenga device asignado
        if (vehicle.hasDevice()) {
            throw new VehicleAlreadyHasDeviceException(command.vehicleId());
        }
        
        // Validar que el device no esté asignado a otro vehículo
        if (device.isAssigned()) {
            throw new DeviceAlreadyAssignedException(command.deviceImei());
        }
        
        // Asignar ambas direcciones de la relación
        vehicle.assignDevice(deviceImei);
        device.assignToVehicle(vehicle.getPlate());
        
        vehicleRepository.save(vehicle);
        deviceRepository.save(device);
        
        return Optional.of(vehicle);
    }
    
    @Override
    @Transactional
    public Optional<Vehicle> handle(UnassignDeviceFromVehicleCommand command) {
        var vehicle = vehicleRepository.findById(command.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(command.vehicleId()));
        
        if (!vehicle.hasDevice()) {
            throw new DeviceAssignmentConflictException("Vehicle has no device assigned");
        }
        
        // Buscar el device asignado y desasignarlo
        var device = deviceRepository.findByImei(vehicle.getDeviceImei())
                .orElseThrow(() -> new DeviceNotFoundException(vehicle.getDeviceImei().value()));
        
        device.unassignFromVehicle();
        vehicle.unassignDevice();
        
        deviceRepository.save(device);
        vehicleRepository.save(vehicle);
        
        return Optional.of(vehicle);
    }
}
