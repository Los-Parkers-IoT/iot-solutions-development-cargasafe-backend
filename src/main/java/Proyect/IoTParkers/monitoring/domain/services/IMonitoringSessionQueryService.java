package Proyect.IoTParkers.monitoring.domain.services;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetActiveSessionQuery;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetMonitoringSessionByIdQuery;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetSessionsByTripIdQuery;

import java.util.List;
import java.util.Optional;

public interface IMonitoringSessionQueryService {
    List<MonitoringSession> handle(GetActiveSessionQuery query);
    MonitoringSession handle(GetMonitoringSessionByIdQuery query);
    MonitoringSession handle(GetSessionsByTripIdQuery query);
}
