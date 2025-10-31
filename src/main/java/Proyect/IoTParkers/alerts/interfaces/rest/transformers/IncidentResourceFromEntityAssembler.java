package Proyect.IoTParkers.alerts.interfaces.rest.transformers;

import Proyect.IoTParkers.alerts.domain.model.entities.Incident;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.IncidentResource;

public class IncidentResourceFromEntityAssembler {
    public static IncidentResource toResourceFromEntity(Incident incident) {
        return new IncidentResource(
                incident.getId(),
                incident.getAlert().getId(),
                incident.getDescription(),
                incident.getCreatedAt(),
                incident.getAcknowledgedAt(),
                incident.getClosedAt()
        );
    }
}
