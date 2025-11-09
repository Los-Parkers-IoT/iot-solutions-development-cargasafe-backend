package Proyect.IoTParkers.trip.domain.services;

import Proyect.IoTParkers.trip.domain.model.entities.OriginPoint;
import Proyect.IoTParkers.trip.domain.model.queries.GetAllOriginPointsQuery;

import java.util.List;

public interface OriginPointQueryService {
    List<OriginPoint> handle(GetAllOriginPointsQuery query);
}
