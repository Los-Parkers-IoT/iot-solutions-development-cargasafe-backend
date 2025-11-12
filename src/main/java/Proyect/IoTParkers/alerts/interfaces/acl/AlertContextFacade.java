package Proyect.IoTParkers.alerts.interfaces.acl;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.commands.CreateAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.NotificationChannel;
import Proyect.IoTParkers.alerts.domain.services.IAlertCommandService;
import Proyect.IoTParkers.alerts.domain.services.IAlertQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AlertContextFacade {

    @Autowired
    private IAlertCommandService alertCommandService;

    public Optional<Long> createAlert(String alertType, String description, String notificationChannel, String message) {

        var type = AlertType.valueOf(alertType.toLowerCase());
        var notification = NotificationChannel.valueOf(notificationChannel.toLowerCase());
        var command = new CreateAlertCommand(type, description, notification, message);
        var alert = alertCommandService.handle(command);

        return Optional.of(alert.get().getId());
    }

}
