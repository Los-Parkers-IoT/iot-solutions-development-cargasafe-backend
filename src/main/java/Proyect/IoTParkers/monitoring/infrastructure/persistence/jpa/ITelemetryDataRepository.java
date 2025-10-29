package Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa;

import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITelemetryDataRepository extends JpaRepository<TelemetryData, Long> {

    @Query("SELECT td FROM TelemetryData td WHERE td.session.id = :sessionId ORDER BY td.createdAt DESC")
    List<TelemetryData> findAllBySessionId(@Param("sessionId") Long sessionId);
}
