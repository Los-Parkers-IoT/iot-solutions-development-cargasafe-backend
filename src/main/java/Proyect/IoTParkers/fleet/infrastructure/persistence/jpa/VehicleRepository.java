package Proyect.IoTParkers.fleet.infrastructure.persistence.jpa;

import Proyect.IoTParkers.fleet.domain.model.aggregates.Vehicle;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.Imei;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.Plate;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleType;
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
    Optional<Vehicle> findByDeviceImeis(Imei deviceImeis);
}
