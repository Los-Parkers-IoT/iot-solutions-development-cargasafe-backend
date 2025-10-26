package Proyect.IoTParkers.alerts.interfaces.rest.transformers;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.AlertResource;

public class AlertResourceFromEntityAssembler {
    public static AlertResource toResourceFromEntity(Alert entity) {
        return new AlertResource(
                entity.getId(),
                entity.getAlertType(),
                entity.getAlertStatus(),
                entity.getIncidents().stream()
                        .map(IncidentResourceFromEntityAssembler::toResourceFromEntity)
                        .toList(),
                entity.getNotifications().stream()
                        .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                        .toList()
        );
    }
}
