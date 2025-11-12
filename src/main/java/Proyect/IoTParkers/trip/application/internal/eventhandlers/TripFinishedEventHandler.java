package Proyect.IoTParkers.trip.application.internal.eventhandlers;

import Proyect.IoTParkers.trip.application.internal.outboundservices.acl.ExternalMonitoringSessionService;
import Proyect.IoTParkers.trip.domain.model.events.TripFinishedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TripFinishedEventHandler {
    @Autowired
    private ExternalMonitoringSessionService externalMonitoringSessionService;


    @EventListener
    public void on(TripFinishedEvent event) {
        var tripId = event.tripId();
        externalMonitoringSessionService.endMonitoringSessionByTripId(tripId);
    }
}
