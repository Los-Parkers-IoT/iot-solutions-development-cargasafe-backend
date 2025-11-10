package Proyect.IoTParkers.trip.domain.services;

import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.commands.CreateTripCommand;

public interface TripCommandService {
    Trip handle(CreateTripCommand command);
}
