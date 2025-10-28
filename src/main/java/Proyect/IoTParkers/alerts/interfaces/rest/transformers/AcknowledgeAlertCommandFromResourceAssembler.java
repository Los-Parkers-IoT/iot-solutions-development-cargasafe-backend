package Proyect.IoTParkers.alerts.interfaces.rest.transformers;

import Proyect.IoTParkers.alerts.domain.model.commands.AcknowledgeAlertCommand;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.AcknowledgeAlertResource;

public class AcknowledgeAlertCommandFromResourceAssembler {
    public static AcknowledgeAlertCommand toCommandFromResource(Long alertId, AcknowledgeAlertResource resource) {
        return new AcknowledgeAlertCommand(alertId);
    }
}
