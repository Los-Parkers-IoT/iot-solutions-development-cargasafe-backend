package Proyect.IoTParkers.fleet.application.internal.commandservices;

import Proyect.IoTParkers.fleet.domain.exceptions.*;
import Proyect.IoTParkers.fleet.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleet.domain.model.commands.*;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.Imei;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.Plate;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleet.domain.services.VehicleCommandService;
import Proyect.IoTParkers.fleet.infrastructure.persistence.jpa.DeviceRepository;
import Proyect.IoTParkers.fleet.infrastructure.persistence.jpa.VehicleRepository;
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

        // restricción de negocio que ya tenías:
        if (vehicle.getStatus() != VehicleStatus.RETIRED) {
            throw new IllegalStateException("Vehicle must be in RETIRED status before deletion");
        }

        // 👇 aquí estaba tu error: antes llamabas a vehicle.hasDevice()
        // ahora el dominio expone hasAnyDevice()
        if (vehicle.hasAnyDevice()) {
            throw new DeviceAssignmentConflictException(
                    "Cannot delete vehicle with assigned devices. Unassign devices first."
            );
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

        // 1) regla de vehículo
        if (vehicle.getStatus() == VehicleStatus.RETIRED) {
            throw new IllegalStateException("Cannot assign device to RETIRED vehicle");
        }

        // 2) regla de device: un device no puede estar en otro vehículo
        if (device.isAssigned()
                && !device.getVehiclePlate().equals(vehicle.getPlate())) {
            throw new DeviceAlreadyAssignedException(command.deviceImei());
        }

        // 3) ACTUALIZO EL DOMINIO VEHICLE (esto te faltaba)
        vehicle.assignDevice(deviceImei);

        // 4) ACTUALIZO EL DEVICE (lo anclo a la placa)
        device.assignToVehicle(vehicle.getPlate());

        // 5) persisto ambos
        vehicleRepository.save(vehicle);
        deviceRepository.save(device);

        return Optional.of(vehicle);
    }

    @Override
    @Transactional
    public Optional<Vehicle> handle(UnassignDeviceFromVehicleCommand command) {
        var vehicle = vehicleRepository.findById(command.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(command.vehicleId()));

        Imei deviceImei = new Imei(command.deviceImei());
        var device = deviceRepository.findByImei(deviceImei)
                .orElseThrow(() -> new DeviceNotFoundException(command.deviceImei()));

        // validar que de verdad esté asignado a ese vehículo
        if (!device.isAssigned() || !device.getVehiclePlate().equals(vehicle.getPlate())) {
            throw new DeviceAssignmentConflictException("Device not assigned to this vehicle");
        }

        // 1) DOMINIO VEHICLE: quitar del set
        vehicle.unassignDevice(deviceImei);

        // 2) DOMINIO DEVICE: quitar la placa
        device.unassignFromVehicle();

        vehicleRepository.save(vehicle);
        deviceRepository.save(device);

        return Optional.of(vehicle);
    }

    @Override
    @Transactional
    public Optional<Vehicle> handle(UpdateVehicleStatusCommand command) {
        var vehicle = vehicleRepository.findById(command.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(command.vehicleId()));

        vehicle.updateStatus(command.status());

        vehicleRepository.save(vehicle);

        return Optional.of(vehicle);
    }



}
