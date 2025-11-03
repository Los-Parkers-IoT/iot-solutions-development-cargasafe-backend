package Proyect.IoTParkers.alerts.domain.services;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.commands.AcknowledgeAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.commands.CreateAlertCommand;

import java.util.Optional;

public interface IAlertCommandService {

    Optional<Alert> handle(CreateAlertCommand command);

    Optional<Alert> handle(AcknowledgeAlertCommand command);

}
