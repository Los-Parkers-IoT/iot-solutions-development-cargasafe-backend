package Proyect.IoTParkers.monitoring.application.internal.commandservices;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.commands.*;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionCommandService;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.IMonitoringSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonitoringCommandServiceImpl implements IMonitoringSessionCommandService {
    private final IMonitoringSessionRepository monitoringSessionRepository;
    public MonitoringCommandServiceImpl(IMonitoringSessionRepository monitoringSessionRepository) {
        this.monitoringSessionRepository = monitoringSessionRepository;
    }


    @Override
    public List<MonitoringSession> handle(StartMonitoringSessionCommand command) {
        return List.of();

    }

    @Override
    public List<MonitoringSession> handle(PauseMonitoringSessionCommand command) {
        return List.of();
    }

    @Override
    public List<MonitoringSession> handle(EndMonitoringSessionCommand command) {
        return List.of();
    }

    @Override
    public List<MonitoringSession> handle(ResumeMonitoringSessionCommand command) {
        return List.of();
    }

    @Override
    public Optional<MonitoringSession> handle(UpdateMonitoringSessionCommand command) {
        return Optional.empty();
    }
}
