package Proyect.IoTParkers.trip.interfaces.rest.resources;

import java.time.LocalDateTime;

public record DeliveryOrderResource(Long id, Long tripId, String clientEmail, Long sequenceOrder,
                                    LocalDateTime arrivalAt, String status) {
}
