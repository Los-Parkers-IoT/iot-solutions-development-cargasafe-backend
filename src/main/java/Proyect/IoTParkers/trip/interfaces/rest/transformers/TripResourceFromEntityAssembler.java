package Proyect.IoTParkers.trip.interfaces.rest.transformers;

import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.interfaces.rest.resources.TripResource;
import org.springframework.stereotype.Component;

@Component
public final class TripResourceFromEntityAssembler {
    static public TripResource toResourceFromEntity(Trip trip) {
        return new TripResource(
                trip.getId(),
                trip.getMerchantId(),
                trip.getDriverId(),
                trip.getVehicleId(),
                trip.getStatus(),
                trip.getCreatedAt(),
                trip.getStartedAt(),
                trip.getCompletedAt()
        );
    }
}

