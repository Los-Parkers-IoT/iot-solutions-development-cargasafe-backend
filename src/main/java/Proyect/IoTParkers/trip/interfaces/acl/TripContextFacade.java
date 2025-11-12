package Proyect.IoTParkers.trip.interfaces.acl;

import Proyect.IoTParkers.trip.domain.model.queries.GetTripByIdQuery;
import Proyect.IoTParkers.trip.domain.services.TripQueryService;
import Proyect.IoTParkers.trip.interfaces.acl.resources.AclTripThresholdValidationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripContextFacade {
    @Autowired
    private TripQueryService tripQueryService;


    public List<AclTripThresholdValidationResource> validateTripThresholds(Long tripId, Double temperature, Double humidity) {
        var maybeTrip = tripQueryService.handle(new GetTripByIdQuery(tripId));

        if (maybeTrip.isEmpty()) {
            throw new IllegalArgumentException("Trip not found with id: " + tripId);
        }

        var trip = maybeTrip.get();
        var deliveryOrders = trip.getDeliveryOrderList();

        var validations = deliveryOrders.stream().map(o -> new AclTripThresholdValidationResource(o.getId(), o.validateThresholds(
                temperature,
                humidity
        ))).toList();

        return validations;
    }
}
