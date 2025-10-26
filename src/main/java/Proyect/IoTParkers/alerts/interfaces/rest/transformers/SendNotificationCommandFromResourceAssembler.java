package Proyect.IoTParkers.alerts.interfaces.rest.transformers;

import Proyect.IoTParkers.alerts.domain.model.commands.SendNotificationCommand;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.SendNotificationResource;

public class SendNotificationCommandFromResourceAssembler {
    public static SendNotificationCommand toCommandFromResource(SendNotificationResource resource) {
        return new SendNotificationCommand(
                resource.alertId(),
                resource.notificationChannel(),
                resource.message()

        );
    }
}
