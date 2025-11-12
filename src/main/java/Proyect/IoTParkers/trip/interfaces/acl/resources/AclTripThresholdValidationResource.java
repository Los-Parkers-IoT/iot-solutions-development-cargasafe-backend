package Proyect.IoTParkers.trip.interfaces.acl.resources;

import java.util.List;

public record AclTripThresholdValidationResource(Long deliveryOrderId, List<String> thresholdType) {
}
