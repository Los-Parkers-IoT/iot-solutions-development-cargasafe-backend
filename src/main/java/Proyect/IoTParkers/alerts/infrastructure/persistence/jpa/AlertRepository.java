package Proyect.IoTParkers.alerts.infrastructure.persistence.jpa;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert,Long> {
}
