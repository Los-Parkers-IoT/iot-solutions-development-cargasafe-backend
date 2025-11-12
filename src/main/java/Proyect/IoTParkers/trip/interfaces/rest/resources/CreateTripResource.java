package Proyect.IoTParkers.trip.interfaces.rest.resources;

import jakarta.validation.Valid;

import java.util.List;


public record CreateTripResource(Long driverId, Long deviceId, Long vehicleId, Long merchantId,
                                 @Valid List<CreateTripDeliveryOrderResource> deliveryOrders, Long originPointId) {
}
