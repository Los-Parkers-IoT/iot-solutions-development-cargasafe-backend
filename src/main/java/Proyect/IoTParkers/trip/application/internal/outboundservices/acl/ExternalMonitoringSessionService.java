package Proyect.IoTParkers.trip.application.internal.outboundservices.acl;

import Proyect.IoTParkers.monitoring.interfaces.acl.MonitoringContextFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalMonitoringSessionService {
    @Autowired
    private MonitoringContextFacade monitoringContextFacade;

    public Long createAndStartMonitoringSession(Long tripId, Long deviceId) {
        return monitoringContextFacade.createAndStartMonitoringSession(tripId, deviceId);
    }

    public void endMonitoringSessionByTripId(Long tripId) {
        monitoringContextFacade.endMonitoringSessionByTripId(tripId);
    }
}
