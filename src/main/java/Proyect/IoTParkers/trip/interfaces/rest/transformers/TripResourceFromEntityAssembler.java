package Proyect.IoTParkers.trip.interfaces.rest.transformers;

import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.interfaces.rest.resources.TripResource;
import org.springframework.stereotype.Component;

@Component
public class TripResourceFromEntityAssembler {
    public TripResource toResource(Trip trip) {
        return new TripResource(
                trip.getId(),
                trip.getMerchantId(),
                trip.getClientId(),
                trip.getDriverId(),
                trip.getVehicleId(),
                trip.getStatus(),
                trip.getCreatedAt(),
                trip.getStartedAt(),
                trip.getCompletedAt()
        );
    }
}

