package Proyect.IoTParkers.alerts.domain.model.commands;

public record CreateIncidentFromAlertCommand (Long alertId, String description){
}
