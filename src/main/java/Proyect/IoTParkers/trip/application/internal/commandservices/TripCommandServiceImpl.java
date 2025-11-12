package Proyect.IoTParkers.trip.application.internal.commandservices;

import Proyect.IoTParkers.trip.domain.exceptions.OriginPointNotFoundException;
import Proyect.IoTParkers.trip.domain.exceptions.TripNotFoundException;
import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.commands.CreateTripCommand;
import Proyect.IoTParkers.trip.domain.model.commands.StartTripCommand;
import Proyect.IoTParkers.trip.domain.model.events.TripStartedEvent;
import Proyect.IoTParkers.trip.domain.services.TripCommandService;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.OriginPointRepository;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class TripCommandServiceImpl implements TripCommandService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private OriginPointRepository originPointRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Override
    public Trip handle(CreateTripCommand command) {
        var originPointId = command.originPointId();
        var maybeOriginPoint = originPointRepository.findById(command.originPointId());

        var originPoint = maybeOriginPoint.orElseThrow(() -> new OriginPointNotFoundException(command.originPointId()));

        var trip = new Trip(command);
        trip.assignOriginPoint(originPoint);

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

        eventPublisher.publishEvent(new TripStartedEvent(trip.getId()));
    }
}
