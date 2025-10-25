package Proyect.IoTParkers.alerts.domain.services;

import Proyect.IoTParkers.alerts.domain.model.commands.CreateIncidentFromAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.entities.Incident;

import java.util.Optional;

public interface IIncidentCommandService {

    Optional<Incident> handle(CreateIncidentFromAlertCommand command);
}
