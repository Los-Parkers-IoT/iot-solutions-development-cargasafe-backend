package Proyect.IoTParkers.alerts.interfaces.rest.transformers;

import Proyect.IoTParkers.alerts.domain.model.commands.CreateAlertCommand;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.CreateAlertResource;

public class CreateAlertCommandFromResourceAssembler {

    public static CreateAlertCommand toCommandFromResource(CreateAlertResource resource){
        return new CreateAlertCommand(
                resource.deliveryOrderId(),
                resource.alertType(),
                resource.description(),
                resource.notificationChannel(),
                resource.message());
    }
}
