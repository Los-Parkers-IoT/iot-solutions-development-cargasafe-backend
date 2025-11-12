package Proyect.IoTParkers.trip.application.internal.commandservices;

import Proyect.IoTParkers.trip.domain.exceptions.DeliveryOrderNotFoundException;
import Proyect.IoTParkers.trip.domain.exceptions.TripNotFoundException;
import Proyect.IoTParkers.trip.domain.model.commands.DeliverDeliveryOrderCommand;
import Proyect.IoTParkers.trip.domain.services.DeliveryOrderCommandService;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.DeliveryOrderRepository;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.TripRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryOrderCommandServiceImpl implements DeliveryOrderCommandService {
    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;
    @Autowired
    private TripRepository tripRepository;


    @Transactional
    @Override
    public void handle(DeliverDeliveryOrderCommand command) {
        var deliveryOrderId = command.deliveryOrderId();
        var maybeDeliveryOrder = deliveryOrderRepository.findById(deliveryOrderId);

        if (maybeDeliveryOrder.isEmpty()) {
            throw new DeliveryOrderNotFoundException(deliveryOrderId);
        }

        var deliveryOrder = maybeDeliveryOrder.get();
        deliveryOrder.markAsDelivered();

        deliveryOrderRepository.save(deliveryOrder);

        var tripId = deliveryOrder.getTrip().getId();
        var maybeTrip = tripRepository.findById(tripId);

        if (maybeTrip.isEmpty()) {
            throw new TripNotFoundException(tripId);
        }

        var trip = maybeTrip.get();

        if (trip.canCompleteTrip()) {
            trip.completeTrip();
        }

        tripRepository.save(trip);

    }
}
