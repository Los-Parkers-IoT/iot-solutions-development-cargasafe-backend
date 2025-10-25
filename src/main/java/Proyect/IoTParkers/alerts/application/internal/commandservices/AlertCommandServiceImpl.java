package Proyect.IoTParkers.alerts.application.internal.commandservices;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.commands.AcknowledgeAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.commands.CreateAlertCommand;
import Proyect.IoTParkers.alerts.domain.services.IAlertCommandService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertCommandServiceImpl implements IAlertCommandService {
    @Override
    public Optional<Alert> handle(CreateAlertCommand command) {
        return Optional.empty();
    }

    @Override
    public Optional<Alert> handle(AcknowledgeAlertCommand command) {
        return Optional.empty();
    }
}
