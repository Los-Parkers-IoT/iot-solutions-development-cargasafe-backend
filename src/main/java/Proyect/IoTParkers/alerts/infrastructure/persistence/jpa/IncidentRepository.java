package Proyect.IoTParkers.alerts.infrastructure.persistence.jpa;

import Proyect.IoTParkers.alerts.domain.model.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident,Long> {
}
