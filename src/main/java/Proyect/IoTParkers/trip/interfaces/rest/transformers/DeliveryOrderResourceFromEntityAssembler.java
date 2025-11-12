package Proyect.IoTParkers.trip.interfaces.rest.transformers;

import Proyect.IoTParkers.trip.domain.model.entities.DeliveryOrder;
import Proyect.IoTParkers.trip.interfaces.rest.resources.DeliveryOrderResource;

public final class DeliveryOrderResourceFromEntityAssembler {
    public static DeliveryOrderResource toResourceFromEntity(DeliveryOrder entity) {
        return new DeliveryOrderResource(entity.getId(), entity.getTrip().getId(), entity.getClientEmail(),
                entity.getSequenceOrder(), entity.getArrivalAt(), entity.getStatus().name());
    }
}
