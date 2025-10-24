package Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa;

import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITelemetryDataRepository extends JpaRepository<TelemetryData, Long> {
}
