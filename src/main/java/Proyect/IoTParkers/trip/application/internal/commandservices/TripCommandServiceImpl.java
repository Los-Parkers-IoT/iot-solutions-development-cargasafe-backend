package Proyect.IoTParkers.trip.application.internal.commandservices;

import Proyect.IoTParkers.trip.domain.exceptions.TripNotFoundException;
import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.commands.CreateTripCommand;
import Proyect.IoTParkers.trip.domain.model.commands.StartTripCommand;
import Proyect.IoTParkers.trip.domain.services.TripCommandService;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripCommandServiceImpl implements TripCommandService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public Trip handle(CreateTripCommand command) {
        var trip = new Trip(command);

        return this.tripRepository.save(trip);
    }

    @Override
    public void handle(StartTripCommand command) {
        var tripId = command.tripId();
        var maybeTrip = tripRepository.findById(command.tripId());

        if (maybeTrip.isEmpty()) {
            throw new TripNotFoundException(tripId);
        }

        var trip = maybeTrip.get();
        trip.startTrip();

        tripRepository.save(trip);
    }
}
