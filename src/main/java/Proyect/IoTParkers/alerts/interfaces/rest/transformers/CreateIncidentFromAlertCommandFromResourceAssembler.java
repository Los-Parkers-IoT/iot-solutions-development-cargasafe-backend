package Proyect.IoTParkers.alerts.interfaces.rest.transformers;

import Proyect.IoTParkers.alerts.domain.model.commands.CreateIncidentFromAlertCommand;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.CreateIncidentFromAlertResource;

public class CreateIncidentFromAlertCommandFromResourceAssembler {
    public static CreateIncidentFromAlertCommand toCommandFromResource(CreateIncidentFromAlertResource resource) {
        return new CreateIncidentFromAlertCommand(resource.alertId(), resource.description());
    }
}
