package Proyect.IoTParkers.trip.domain.services;

import Proyect.IoTParkers.trip.domain.model.entities.DeliveryOrder;
import Proyect.IoTParkers.trip.domain.model.queries.DeliveryOrderExistsQuery;
import Proyect.IoTParkers.trip.domain.model.queries.GetAllDeliveryOrdersQuery;

import java.util.List;

public interface DeliveryOrderQueryService {
    List<DeliveryOrder> handle(GetAllDeliveryOrdersQuery query);
    boolean handle(DeliveryOrderExistsQuery query);
}
