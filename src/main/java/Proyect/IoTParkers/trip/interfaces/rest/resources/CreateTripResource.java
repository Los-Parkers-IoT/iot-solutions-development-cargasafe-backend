package Proyect.IoTParkers.trip.interfaces.rest.resources;

import java.util.List;


public record CreateTripResource(Long driverId, Long deviceId, Long vehicleId, Long merchantId,
                                 List<CreateTripDeliveryOrderResource> deliveryOrders) {
}
