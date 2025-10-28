package Proyect.IoTParkers.alerts.domain.services;

import Proyect.IoTParkers.alerts.domain.model.commands.SendNotificationCommand;

public interface INotificationCommandService {

    void handle(SendNotificationCommand command);

}
