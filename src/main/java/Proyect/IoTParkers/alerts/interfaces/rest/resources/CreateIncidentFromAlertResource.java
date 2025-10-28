package Proyect.IoTParkers.alerts.interfaces.rest.resources;

public record CreateIncidentFromAlertResource(
        Long alertId,
        String description
) {
}
