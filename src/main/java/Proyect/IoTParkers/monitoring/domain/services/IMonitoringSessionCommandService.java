package Proyect.IoTParkers.monitoring.domain.services;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.commands.*;

import java.util.List;
import java.util.Optional;

public interface IMonitoringSessionCommandService {
    MonitoringSession handle(StartMonitoringSessionCommand command);
    MonitoringSession handle(PauseMonitoringSessionCommand command);
    MonitoringSession handle(EndMonitoringSessionCommand command);
    MonitoringSession handle(ResumeMonitoringSessionCommand command);
}
