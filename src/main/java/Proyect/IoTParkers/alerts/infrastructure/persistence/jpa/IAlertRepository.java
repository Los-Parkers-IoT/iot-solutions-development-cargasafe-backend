package Proyect.IoTParkers.alerts.infrastructure.persistence.jpa;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertStatus;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlertRepository extends JpaRepository<Alert,Long> {

    List<Alert> findByAlertType(AlertType alertType);
    List<Alert> findByAlertStatus(AlertStatus alertStatus);

}
