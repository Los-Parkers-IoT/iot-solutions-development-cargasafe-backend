package Proyect.IoTParkers.trip.domain.services;

import Proyect.IoTParkers.trip.domain.model.commands.CreateOriginPointCommand;
import Proyect.IoTParkers.trip.domain.model.entities.OriginPoint;

public interface OriginPointCommandService {
    OriginPoint handle(CreateOriginPointCommand command);
}
