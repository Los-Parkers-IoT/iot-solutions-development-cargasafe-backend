package Proyect.IoTParkers.trip.interfaces.rest.transformers;

import Proyect.IoTParkers.trip.domain.model.entities.DeliveryOrder;
import Proyect.IoTParkers.trip.domain.model.valueobjects.OrderThresholds;
import Proyect.IoTParkers.trip.interfaces.rest.resources.DeliveryOrderResource;

import java.util.Optional;

public final class DeliveryOrderResourceFromEntityAssembler {
    public static DeliveryOrderResource toResourceFromEntity(DeliveryOrder entity) {
        System.out.println("Transforming DeliveryOrder entity to DeliveryOrderResource: " + entity);

        var thresholds = entity.getOrderThresholds();
        var location = entity.getLocation();


        var minHumidity = Optional.ofNullable(thresholds).map(OrderThresholds::minHumidity).orElse(null);
        var maxHumidity = Optional.ofNullable(thresholds).map(OrderThresholds::maxHumidity).orElse(null);
        var minTemperature = Optional.ofNullable(thresholds).map(OrderThresholds::minTemperature).orElse(null);
        var maxTemperature = Optional.ofNullable(thresholds).map(OrderThresholds::maxTemperature).orElse(null);
        var maxVibration = Optional.ofNullable(thresholds).map(OrderThresholds::maxVibration).orElse(null);

        return new DeliveryOrderResource(
                entity.getId(),
                entity.getTrip().getId(),
                entity.getClientEmail(),
                entity.getSequenceOrder(),
                location.address(),
                entity.getArrivalAt(),
                entity.getStatus().name(),
                minHumidity,
                maxTemperature,
                minHumidity,
                maxTemperature,
                maxVibration,
                location.latitude(),
                location.longitude(),
                entity.getCreatedAt()
        );
    }
}
