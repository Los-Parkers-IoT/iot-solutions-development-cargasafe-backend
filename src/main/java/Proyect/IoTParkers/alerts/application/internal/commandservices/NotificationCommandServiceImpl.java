package Proyect.IoTParkers.alerts.application.internal.commandservices;

import Proyect.IoTParkers.alerts.domain.exceptions.AlertNotFoundException;
import Proyect.IoTParkers.alerts.domain.model.commands.SendNotificationCommand;
import Proyect.IoTParkers.alerts.domain.model.entities.Notification;
import Proyect.IoTParkers.alerts.domain.services.INotificationCommandService;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.IAlertRepository;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.INotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationCommandServiceImpl implements INotificationCommandService {

    private final INotificationRepository notificationRepository;
    private final IAlertRepository alertRepository;

    public NotificationCommandServiceImpl(INotificationRepository notificationRepository, IAlertRepository alertRepository) {
        this.notificationRepository = notificationRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public void handle(SendNotificationCommand command) {
        var alert = alertRepository.findById(command.alertId())
                .orElseThrow(() -> new AlertNotFoundException(command.alertId()));

        var notification = new Notification(command, alert);

        try{
            notificationRepository.save(notification);
        } catch (Exception e){
            throw new IllegalArgumentException("Error sending notification: " + e.getMessage());
        }

    }
}
