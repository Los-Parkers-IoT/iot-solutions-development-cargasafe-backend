package Proyect.IoTParkers.monitoring.application.internal.outboundservices.acl;

import Proyect.IoTParkers.alerts.interfaces.acl.AlertContextFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalAlertService {
    @Autowired
    private AlertContextFacade alertContextFacade;

    public void sendAlertNotification(String alertType, String description, String notificationChannel, String message) {
        this.alertContextFacade.createAlert(alertType, description, notificationChannel, message);
    }
}
