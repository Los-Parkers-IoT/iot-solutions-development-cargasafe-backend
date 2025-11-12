package Proyect.IoTParkers.monitoring.application.internal.outboundservices.acl;

import Proyect.IoTParkers.trip.interfaces.acl.TripContextFacade;
import Proyect.IoTParkers.trip.interfaces.acl.resources.AclTripThresholdValidationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalTripService {
    @Autowired
    private TripContextFacade tripContextFacade;


    public List<AclTripThresholdValidationResource> validateTripThresholds(Long tripId, Double temperature, Double humidity) {
        var validations = this.tripContextFacade.validateTripThresholds(tripId, temperature, humidity);

        System.out.println("Validations received from TripContextFacade: " + validations);

        return validations;
    }
}
