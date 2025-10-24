package Proyect.IoTParkers.monitoring.domain.services;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.commands.*;

import java.util.List;
import java.util.Optional;

public interface IMonitoringSessionCommandService {
    List<MonitoringSession> handle(StartMonitoringSessionCommand command);
    List<MonitoringSession> handle (PauseMonitoringSessionCommand command);
    List<MonitoringSession> handle (EndMonitoringSessionCommand command);
    List <MonitoringSession> handle (ResumeMonitoringSessionCommand command);
    Optional<MonitoringSession> handle (UpdateMonitoringSessionCommand command);
}
