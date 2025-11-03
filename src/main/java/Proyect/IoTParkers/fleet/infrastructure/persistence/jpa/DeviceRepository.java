package Proyect.IoTParkers.fleet.infrastructure.persistence.jpa;

import Proyect.IoTParkers.fleet.domain.model.aggregates.Device;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.DeviceType;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.Imei;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.Plate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByImei(Imei imei);
    boolean existsByImei(Imei imei);
    List<Device> findAllByOnline(boolean online);
    List<Device> findAllByType(DeviceType type);
    List<Device> findAllByVehiclePlate(Plate vehiclePlate);
}
