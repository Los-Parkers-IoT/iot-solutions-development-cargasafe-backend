package Proyect.IoTParkers.trip.domain.services;

import Proyect.IoTParkers.trip.domain.model.commands.DeliverDeliveryOrderCommand;

public interface DeliveryOrderCommandService {
    void handle(DeliverDeliveryOrderCommand command);
}
