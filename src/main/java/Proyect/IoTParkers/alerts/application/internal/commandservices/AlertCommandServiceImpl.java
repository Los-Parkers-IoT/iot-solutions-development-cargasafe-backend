package Proyect.IoTParkers.alerts.application.internal.commandservices;

import Proyect.IoTParkers.alerts.domain.exceptions.AlertCreationException;
import Proyect.IoTParkers.alerts.domain.exceptions.AlertNotFoundException;
import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.commands.AcknowledgeAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.commands.CloseAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.commands.CreateAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.entities.Notification;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertStatus;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.NotificationChannel;
import Proyect.IoTParkers.alerts.domain.services.IAlertCommandService;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.IAlertRepository;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.IIncidentRepository;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.INotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AlertCommandServiceImpl implements IAlertCommandService {

    private final IAlertRepository alertRepository;
    private final INotificationRepository notificationRepository;

    public AlertCommandServiceImpl(IAlertRepository alertRepository, INotificationRepository notificationRepository) {
        this.alertRepository = alertRepository;
        this.notificationRepository = notificationRepository;
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

    @Override
    public Optional<Alert> handle(CloseAlertCommand command) {
        var alert = alertRepository.findById(command.alertId())
                .orElseThrow(() -> new AlertNotFoundException(command.alertId()));

        if (alert.getAlertStatus() == AlertStatus.OPEN) {
            throw new IllegalStateException("Cannot close an alert that has not been acknowledged first.");
        }

        if (alert.getAlertStatus() == AlertStatus.CLOSED) {
            throw new IllegalStateException("This alert is already closed.");
        }
        alert.close();

        var notification = new Notification(
                alert,
                NotificationChannel.EMAIL,
                "Alert " + alert.getId() + " has been closed.",
                LocalDateTime.now()
        );

        alert.getNotifications().add(notification);
        notificationRepository.save(notification);

        var updatedAlert = alertRepository.save(alert);
        return Optional.of(updatedAlert);
    }
}
