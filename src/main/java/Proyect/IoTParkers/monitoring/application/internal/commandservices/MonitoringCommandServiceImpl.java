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
    public MonitoringSession handle(StartMonitoringSessionCommand command) {
        var activeStatus = monitoringSessionRepository.findActiveStatus()
                .orElseThrow(() -> new IllegalStateException("Active status not found"));

        var session = new MonitoringSession(command, activeStatus);

        try {
            monitoringSessionRepository.save(session);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving monitoring session: " + e.getMessage());
        }
        return session;
    }

    @Override
    public MonitoringSession handle(PauseMonitoringSessionCommand command) {
        var session = monitoringSessionRepository.findById(command.sessionId())
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        var pausedStatus = monitoringSessionRepository.findPausedStatus()
                .orElseThrow(() -> new IllegalStateException("Paused status not found"));

        session.pause(pausedStatus);

        try {
            monitoringSessionRepository.save(session);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while pausing session: " + e.getMessage());
        }
        return session;
    }

    @Override
    public MonitoringSession handle(EndMonitoringSessionCommand command) {
        var session = monitoringSessionRepository.findById(command.sessionId())
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        var completedStatus = monitoringSessionRepository.findCompletedStatus()
                .orElseThrow(() -> new IllegalStateException("Completed status not found"));

        session.complete(completedStatus);

        try {
            monitoringSessionRepository.save(session);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while completing session: " + e.getMessage());
        }
        return session;
    }

    @Override
    public MonitoringSession handle(ResumeMonitoringSessionCommand command) {
        var session = monitoringSessionRepository.findById(command.sessionId())
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        var activeStatus = monitoringSessionRepository.findActiveStatus()
                .orElseThrow(() -> new IllegalStateException("Active status not found"));

        session.resume(activeStatus);

        try {
            monitoringSessionRepository.save(session);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while resuming session: " + e.getMessage());
        }
        return session;
    }
}
