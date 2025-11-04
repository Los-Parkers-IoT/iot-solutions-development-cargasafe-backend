package Proyect.IoTParkers.trip.interfaces.rest.transformers;

import Proyect.IoTParkers.trip.interfaces.rest.resources.CreateTripResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class CreateTripCommandFromResourceAssembler {
    public Long merchantId(CreateTripResource r) { return r.merchantId(); }
    public Instant createdAt(CreateTripResource r) { return r.createdAt(); }
}
