package Proyect.IoTParkers.monitoring.interfaces.rest.transformers.monitoring;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.interfaces.rest.resources.monitoring.MonitoringSessionResource;

public class MonitoringSessionResourceFromEntityAssembler {
    public static MonitoringSessionResource toResourceFromEntity(MonitoringSession entity) {
        return new MonitoringSessionResource(
                entity.getId(),
                entity.getDeviceId(),
                entity.getTripId(),
                entity.getStartTime() != null ? entity.getStartTime().toString() : null,
                entity.getEndTime() != null ? entity.getEndTime().toString() : null,
                entity.getSessionStatus().name()
        );
    }
}
