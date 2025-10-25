package Proyect.IoTParkers.alerts.application.internal.queryservices;

import Proyect.IoTParkers.alerts.domain.model.entities.Notification;
import Proyect.IoTParkers.alerts.domain.model.queries.GetNotificationsByAlertIdQuery;
import Proyect.IoTParkers.alerts.domain.services.INotificationQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationQueryServiceImpl implements INotificationQueryService {
    @Override
    public List<Notification> handle(GetNotificationsByAlertIdQuery query) {
        return List.of();
    }
}
