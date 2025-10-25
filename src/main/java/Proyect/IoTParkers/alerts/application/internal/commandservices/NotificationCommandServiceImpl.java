package Proyect.IoTParkers.alerts.application.internal.commandservices;

import Proyect.IoTParkers.alerts.domain.model.commands.SendNotificationCommand;
import Proyect.IoTParkers.alerts.domain.services.INotificationCommandService;
import org.springframework.stereotype.Service;

@Service
public class NotificationCommandServiceImpl implements INotificationCommandService {
    @Override
    public void handle(SendNotificationCommand command) {

    }
}
