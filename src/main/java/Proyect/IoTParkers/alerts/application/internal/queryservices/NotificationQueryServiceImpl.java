package Proyect.IoTParkers.alerts.application.internal.queryservices;

import Proyect.IoTParkers.alerts.domain.model.entities.Notification;
import Proyect.IoTParkers.alerts.domain.model.queries.GetNotificationsByAlertIdQuery;
import Proyect.IoTParkers.alerts.domain.services.INotificationQueryService;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.INotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationQueryServiceImpl implements INotificationQueryService {

    private final INotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> handle(GetNotificationsByAlertIdQuery query) {
        return notificationRepository.findByAlertId(query.alertId());
    }
}
