package Proyect.IoTParkers.alerts.domain.services;

import Proyect.IoTParkers.alerts.domain.model.entities.Notification;
import Proyect.IoTParkers.alerts.domain.model.queries.GetNotificationsByAlertIdQuery;

import java.util.List;

public interface INotificationQueryService {

    List<Notification> handle(GetNotificationsByAlertIdQuery query);
}
