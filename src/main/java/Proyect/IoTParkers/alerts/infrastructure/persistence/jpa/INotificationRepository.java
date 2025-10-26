package Proyect.IoTParkers.alerts.infrastructure.persistence.jpa;

import Proyect.IoTParkers.alerts.domain.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByAlertId(Long alertId);
}
