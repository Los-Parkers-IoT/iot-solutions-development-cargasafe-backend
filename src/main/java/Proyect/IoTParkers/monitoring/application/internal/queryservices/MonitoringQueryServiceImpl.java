package Proyect.IoTParkers.monitoring.application.internal.queryservices;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetActiveSessionQuery;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetMonitoringSessionByIdQuery;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetSessionsByTripIdQuery;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionQueryService;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.IMonitoringSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonitoringQueryServiceImpl implements IMonitoringSessionQueryService {

    private final IMonitoringSessionRepository monitoringSessionRepository;

    public MonitoringQueryServiceImpl(IMonitoringSessionRepository monitoringSessionRepository) {
        this.monitoringSessionRepository = monitoringSessionRepository;
    }

    @Override
    public List<MonitoringSession> handle(GetActiveSessionQuery query) {
        return monitoringSessionRepository.getActiveSession();
    }

    @Override
    public MonitoringSession handle(GetMonitoringSessionByIdQuery query) {
        return monitoringSessionRepository.findById(query.sessionId())
                .orElseThrow(() -> new RuntimeException("Monitoring session not found for id: " + query.sessionId()));
    }

    @Override
    public MonitoringSession handle(GetSessionsByTripIdQuery query) {
        return monitoringSessionRepository.getSessionsByTripId(query.tripId())
                .orElseThrow(() -> new RuntimeException("Monitoring session not found for tripId: " + query.tripId()));
    }
}
