package Proyect.IoTParkers.alerts.interfaces.rest.transformers;

import Proyect.IoTParkers.alerts.domain.model.commands.CloseAlertCommand;

public class CloseAlertCommandFromResourceAssembler {
    public static CloseAlertCommand toCommandFromResource(Long alertId) {
        return new CloseAlertCommand(alertId);
    }
}
