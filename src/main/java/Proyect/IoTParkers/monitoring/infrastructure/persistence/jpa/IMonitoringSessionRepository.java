package Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMonitoringSessionRepository extends JpaRepository<MonitoringSession, Long> {

}
