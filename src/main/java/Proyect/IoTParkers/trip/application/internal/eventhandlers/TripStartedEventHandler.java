package Proyect.IoTParkers.trip.application.internal.eventhandlers;

import Proyect.IoTParkers.trip.application.internal.outboundservices.acl.ExternalMonitoringSessionService;
import Proyect.IoTParkers.trip.domain.model.events.TripStartedEvent;
import Proyect.IoTParkers.trip.domain.model.valueobjects.DeliveryOrderStatus;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.DeliveryOrderRepository;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TripStartedEventHandler {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;
    @Autowired
    private ExternalMonitoringSessionService externalMonitoringSessionService;

    @EventListener
    public void on(TripStartedEvent event) {

        var tripId = event.tripId();
        var maybeTrip = tripRepository.findById(tripId);

        if (maybeTrip.isEmpty()) return;
        var trip = maybeTrip.get();

        var deliveryOrders = deliveryOrderRepository.findByTripId(tripId);
        deliveryOrders.forEach(o -> o.setStatus(DeliveryOrderStatus.PENDING));
        externalMonitoringSessionService.createAndStartMonitoringSession(trip.getId(), trip.getDeviceId());

    }
}
