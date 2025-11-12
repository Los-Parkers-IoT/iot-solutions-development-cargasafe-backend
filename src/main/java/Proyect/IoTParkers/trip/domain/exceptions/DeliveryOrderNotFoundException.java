package Proyect.IoTParkers.trip.domain.exceptions;

public class DeliveryOrderNotFoundException extends RuntimeException {
    public DeliveryOrderNotFoundException(Long deliveryOrderId) {
        super("Delivery order with id " + deliveryOrderId.toString() + " not found.");
    }
}
