package Proyect.IoTParkers.alerts.interfaces.rest.transformers;

import Proyect.IoTParkers.alerts.domain.model.commands.AcknowledgeAlertCommand;

public class AcknowledgeAlertCommandFromResourceAssembler {
    public static AcknowledgeAlertCommand toCommandFromResource(Long alertId) {
        return new AcknowledgeAlertCommand(alertId);
    }
}
