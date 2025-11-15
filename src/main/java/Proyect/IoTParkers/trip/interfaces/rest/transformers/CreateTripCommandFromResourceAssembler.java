package Proyect.IoTParkers.trip.interfaces.rest.transformers;

import Proyect.IoTParkers.trip.domain.model.commands.CreateTripCommand;
import Proyect.IoTParkers.trip.domain.model.entities.DeliveryOrder;
import Proyect.IoTParkers.trip.domain.model.valueobjects.Location;
import Proyect.IoTParkers.trip.domain.model.valueobjects.OrderThresholds;
import Proyect.IoTParkers.trip.interfaces.rest.resources.CreateTripResource;
import org.springframework.stereotype.Component;

@Component
public final class CreateTripCommandFromResourceAssembler {
    static public CreateTripCommand toCommandFromResource(CreateTripResource resource) {

        var deliveryOrders = resource.deliveryOrders().stream().map(o -> {
            var entity = new DeliveryOrder();
            entity.setSequenceOrder(o.sequenceOrder());
            entity.setClientEmail(o.clientEmail());

            entity.setOrderThresholds(new OrderThresholds(o.minHumidity(), o.maxHumidity(), o.maxTemperature(), o.minTemperature(), o.maxVibration()));
            entity.setLocation(new Location(o.address(), o.latitude(), o.longitude()));

            return entity;
        }).toList();

        return new CreateTripCommand(resource.driverId(), resource.deviceId(), resource.vehicleId(), resource.merchantId(), deliveryOrders, resource.originPointId());
    }
}
