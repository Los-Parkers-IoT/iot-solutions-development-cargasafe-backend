package Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMonitoringSessionRepository extends JpaRepository<MonitoringSession, Long> {
    @Query("SELECT ms FROM MonitoringSession ms WHERE ms.tripId = :tripId")
    Optional<MonitoringSession> getSessionsByTripId(@Param("tripId") Long tripId);
    List<MonitoringSession> getActiveSession();
}
