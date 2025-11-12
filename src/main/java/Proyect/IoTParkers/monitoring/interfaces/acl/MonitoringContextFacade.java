package Proyect.IoTParkers.monitoring.interfaces.acl;

import Proyect.IoTParkers.monitoring.domain.model.commands.EndMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetSessionsByTripIdQuery;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionCommandService;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitoringContextFacade {
    @Autowired
    private IMonitoringSessionCommandService monitoringSessionCommandService;
    @Autowired
    private IMonitoringSessionQueryService monitoringSessionQueryService;


    public Long createAndStartMonitoringSession(Long tripId, Long deviceId) {
        var command = new StartMonitoringSessionCommand(tripId, deviceId);
        var session = monitoringSessionCommandService.handle(command);
        return session.getId();
    }

    public void endMonitoringSessionByTripId(Long tripId) {
        var query = new GetSessionsByTripIdQuery(tripId);
        var session = monitoringSessionQueryService.handle(query);

        var command = new EndMonitoringSessionCommand(session.getId());
        monitoringSessionCommandService.handle(command);
    }
}
