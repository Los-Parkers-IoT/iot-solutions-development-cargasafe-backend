package Proyect.IoTParkers.fleetmanagement.application.internal.queryservices;

import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleetmanagement.domain.model.queries.*;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.Plate;
import Proyect.IoTParkers.fleetmanagement.domain.services.VehicleQueryService;
import Proyect.IoTParkers.fleetmanagement.infrastructure.persistence.jpa.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {
    
    private final VehicleRepository vehicleRepository;
    
    public VehicleQueryServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    
    @Override
    public List<Vehicle> handle(GetAllVehiclesQuery query) {
        return vehicleRepository.findAll();
    }
    
    @Override
    public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
        return vehicleRepository.findById(query.id());
    }
    
    @Override
    public Optional<Vehicle> handle(GetVehicleByPlateQuery query) {
        Plate plate = new Plate(query.plate());
        return vehicleRepository.findByPlate(plate);
    }
    
    @Override
    public List<Vehicle> handle(GetVehiclesByStatusQuery query) {
        return vehicleRepository.findAllByStatus(query.status());
    }
    
    @Override
    public List<Vehicle> handle(GetVehiclesByTypeQuery query) {
        return vehicleRepository.findAllByType(query.type());
    }
}
