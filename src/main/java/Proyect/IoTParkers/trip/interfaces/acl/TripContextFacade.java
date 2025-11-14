package Proyect.IoTParkers.trip.interfaces.acl;

import Proyect.IoTParkers.trip.domain.model.queries.DeliveryOrderExistsQuery;
import Proyect.IoTParkers.trip.domain.model.queries.GetTripByIdQuery;
import Proyect.IoTParkers.trip.domain.services.DeliveryOrderQueryService;
import Proyect.IoTParkers.trip.domain.services.TripQueryService;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.DeliveryOrderRepository;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.TripRepository;
import Proyect.IoTParkers.trip.interfaces.acl.resources.AclTripThresholdValidationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripContextFacade {
    @Autowired
    private TripQueryService tripQueryService;
    @Autowired
    private DeliveryOrderQueryService deliveryOrderQueryService;


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

    public boolean deliveryOrderExists(Long deliveryOrderId) {
        var query = new DeliveryOrderExistsQuery(deliveryOrderId);
        return deliveryOrderQueryService.handle(query);
    }
}
