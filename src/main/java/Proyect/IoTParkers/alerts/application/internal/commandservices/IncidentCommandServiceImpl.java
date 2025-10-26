package Proyect.IoTParkers.alerts.application.internal.commandservices;

import Proyect.IoTParkers.alerts.domain.exceptions.AlertNotFoundException;
import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.commands.CreateIncidentFromAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.entities.Incident;
import Proyect.IoTParkers.alerts.domain.services.IIncidentCommandService;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.IAlertRepository;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.IIncidentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncidentCommandServiceImpl implements IIncidentCommandService {

    private final IIncidentRepository incidentRepository;
    private final IAlertRepository alertRepository;

    public IncidentCommandServiceImpl(IIncidentRepository incidentRepository, IAlertRepository alertRepository) {
        this.incidentRepository = incidentRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Incident> handle(CreateIncidentFromAlertCommand command) {

        Alert alert = alertRepository.findById(command.alertId())
                .orElseThrow(() -> new AlertNotFoundException(command.alertId()));

        Incident incident = new Incident(command,alert);

        var savedIncident = incidentRepository.save(incident);

        return Optional.of(savedIncident);
    }
}
