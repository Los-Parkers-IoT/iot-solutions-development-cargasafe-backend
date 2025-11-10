package Proyect.IoTParkers.trip.domain.model.commands;

import Proyect.IoTParkers.trip.domain.model.entities.DeliveryOrder;

import java.util.List;

public record CreateTripCommand(Long driverId, Long deviceId, Long vehicleId, Long merchantId,
                                List<DeliveryOrder> deliveryOrderList) {
}