package Proyect.IoTParkers.alerts.interfaces.rest.transformers;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.AlertResource;

public class AlertResourceFromEntityAssembler {
    public static AlertResource toResourceFromEntity(Alert alert) {
        return new AlertResource(
                alert.getId(), alert.getAlertType(),alert.getAlertStatus()
        );
    }
}
