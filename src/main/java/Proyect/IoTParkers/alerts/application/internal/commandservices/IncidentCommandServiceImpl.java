package Proyect.IoTParkers.alerts.application.internal.commandservices;

import Proyect.IoTParkers.alerts.domain.model.commands.CreateIncidentFromAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.entities.Incident;
import Proyect.IoTParkers.alerts.domain.services.IIncidentCommandService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncidentCommandServiceImpl implements IIncidentCommandService {
    @Override
    public Optional<Incident> handle(CreateIncidentFromAlertCommand command) {
        return Optional.empty();
    }
}
