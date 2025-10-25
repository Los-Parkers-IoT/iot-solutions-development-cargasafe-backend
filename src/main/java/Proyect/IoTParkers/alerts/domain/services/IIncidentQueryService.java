package Proyect.IoTParkers.alerts.domain.services;

import Proyect.IoTParkers.alerts.domain.model.entities.Incident;
import Proyect.IoTParkers.alerts.domain.model.queries.GetIncidentsByAlertIdQuery;

import java.util.List;

public interface IIncidentQueryService {

    List<Incident> handle(GetIncidentsByAlertIdQuery query);

}
