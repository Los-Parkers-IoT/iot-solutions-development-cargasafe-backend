package Proyect.IoTParkers.trip.interfaces.rest.transformers;

import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import Proyect.IoTParkers.trip.interfaces.rest.resources.UpdateTripStatusResource;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UpdateTripStatusCommandFromResourceAssembler {
    public TripStatus status(UpdateTripStatusResource r) { return r.status(); }
    public Instant startedAt(UpdateTripStatusResource r) { return r.startedAt(); }
    public Instant completedAt(UpdateTripStatusResource r) { return r.completedAt(); }
}
