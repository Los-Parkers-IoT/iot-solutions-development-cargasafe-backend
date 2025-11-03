package Proyect.IoTParkers.monitoring.application.internal.queryservices;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetActiveSessionQuery;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetMonitoringSessionByIdQuery;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetSessionsByTripIdQuery;
import Proyect.IoTParkers.monitoring.domain.model.valueobjects.MonitoringSessionStatus;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionQueryService;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.IMonitoringSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringQueryServiceImpl implements IMonitoringSessionQueryService {

    private final IMonitoringSessionRepository monitoringSessionRepository;

    public MonitoringQueryServiceImpl(IMonitoringSessionRepository monitoringSessionRepository) {
        this.monitoringSessionRepository = monitoringSessionRepository;
    }

    @Override
    public List<MonitoringSession> handle(GetActiveSessionQuery query) {
        return monitoringSessionRepository.findBySessionStatus(MonitoringSessionStatus.ACTIVE);
    }

    @Override
    public MonitoringSession handle(GetMonitoringSessionByIdQuery query) {
        return monitoringSessionRepository.findById(query.sessionId())
                .orElseThrow(() -> new RuntimeException("Monitoring session not found for id: " + query.sessionId()));
    }

    @Override
    public MonitoringSession handle(GetSessionsByTripIdQuery query) {
        return monitoringSessionRepository.findByTripId(query.tripId().toString())
                .orElseThrow(() -> new RuntimeException("Monitoring session not found for tripId: " + query.tripId()));
    }
}
