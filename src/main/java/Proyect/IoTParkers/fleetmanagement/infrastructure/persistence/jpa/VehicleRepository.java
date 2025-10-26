package Proyect.IoTParkers.fleetmanagement.infrastructure.persistence.jpa;

import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.Imei;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.Plate;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByPlate(Plate plate);
    boolean existsByPlate(Plate plate);
    List<Vehicle> findAllByStatus(VehicleStatus status);
    List<Vehicle> findAllByType(VehicleType type);
    Optional<Vehicle> findByDeviceImei(Imei deviceImei);
}
