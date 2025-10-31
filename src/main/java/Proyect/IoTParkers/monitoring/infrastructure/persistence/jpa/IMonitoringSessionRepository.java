package Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.valueobjects.MonitoringSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMonitoringSessionRepository extends JpaRepository<MonitoringSession, Long> {
    Optional<MonitoringSession> findByDeviceId(String deviceId);
    List<MonitoringSession> findBySessionStatus(MonitoringSessionStatus status);
    Optional<MonitoringSession> findFirstByDeviceIdOrderByCreatedAtDesc(String deviceId);
    Optional<MonitoringSession> findByTripId(String tripId);
}