package Proyect.IoTParkers.alerts.application.internal.commandservices;

import Proyect.IoTParkers.alerts.domain.exceptions.AlertCreationException;
import Proyect.IoTParkers.alerts.domain.exceptions.AlertNotFoundException;
import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.commands.AcknowledgeAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.commands.CreateAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertStatus;
import Proyect.IoTParkers.alerts.domain.services.IAlertCommandService;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.IAlertRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertCommandServiceImpl implements IAlertCommandService {

    private final IAlertRepository alertRepository;

    public AlertCommandServiceImpl(IAlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Alert> handle(CreateAlertCommand command) {
        try{
            var alert = new Alert(command);
            var savedAlert = alertRepository.save(alert);
            return  Optional.of(savedAlert);
        } catch (Exception e){
            throw new AlertCreationException("Failed to create alert: " + e.getMessage());
        }
    }

    @Override
    public Optional<Alert> handle(AcknowledgeAlertCommand command) {

        var alert = alertRepository.findById(command.alertId())
                .orElseThrow(() -> new AlertNotFoundException(command.alertId()));

        alert.acknowledge(AlertStatus.ACKNOWLEDGED);

        var updatedAlert = alertRepository.save(alert);
        return Optional.of(updatedAlert);
    }
}
