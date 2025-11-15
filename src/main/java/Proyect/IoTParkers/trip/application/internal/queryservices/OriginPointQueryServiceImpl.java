package Proyect.IoTParkers.trip.application.internal.queryservices;

import Proyect.IoTParkers.trip.domain.model.entities.OriginPoint;
import Proyect.IoTParkers.trip.domain.model.queries.GetAllOriginPointsQuery;
import Proyect.IoTParkers.trip.domain.services.OriginPointQueryService;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.OriginPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OriginPointQueryServiceImpl implements OriginPointQueryService {

    @Autowired
    private OriginPointRepository originPointRepository;

    @Override
    public List<OriginPoint> handle(GetAllOriginPointsQuery query) {
        return originPointRepository.findAll();
    }
}
