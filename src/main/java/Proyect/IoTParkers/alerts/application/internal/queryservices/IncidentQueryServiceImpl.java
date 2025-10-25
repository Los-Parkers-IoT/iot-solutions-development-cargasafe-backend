package Proyect.IoTParkers.alerts.application.internal.queryservices;

import Proyect.IoTParkers.alerts.domain.model.entities.Incident;
import Proyect.IoTParkers.alerts.domain.model.queries.GetIncidentsByAlertIdQuery;
import Proyect.IoTParkers.alerts.domain.services.IIncidentQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentQueryServiceImpl implements IIncidentQueryService {
    @Override
    public List<Incident> handle(GetIncidentsByAlertIdQuery query) {
        return List.of();
    }
}
