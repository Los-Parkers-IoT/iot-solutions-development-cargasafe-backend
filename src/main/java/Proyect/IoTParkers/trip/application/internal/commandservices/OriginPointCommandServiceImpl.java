package Proyect.IoTParkers.trip.application.internal.commandservices;

import Proyect.IoTParkers.trip.domain.model.commands.CreateOriginPointCommand;
import Proyect.IoTParkers.trip.domain.model.entities.OriginPoint;
import Proyect.IoTParkers.trip.domain.services.OriginPointCommandService;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.OriginPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginPointCommandServiceImpl implements OriginPointCommandService {
    @Autowired
    private OriginPointRepository originPointRepository;

    @Override
    public OriginPoint handle(CreateOriginPointCommand command) {
        var originPoint = new OriginPoint(command);

        return originPointRepository.save(originPoint);
    }
}
