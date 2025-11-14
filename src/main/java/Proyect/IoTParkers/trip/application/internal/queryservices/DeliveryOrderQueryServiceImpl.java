package Proyect.IoTParkers.trip.application.internal.queryservices;

import Proyect.IoTParkers.trip.domain.model.entities.DeliveryOrder;
import Proyect.IoTParkers.trip.domain.model.queries.DeliveryOrderExistsQuery;
import Proyect.IoTParkers.trip.domain.model.queries.GetAllDeliveryOrdersQuery;
import Proyect.IoTParkers.trip.domain.services.DeliveryOrderQueryService;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.DeliveryOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryOrderQueryServiceImpl implements DeliveryOrderQueryService {
    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;

    @Override
    public List<DeliveryOrder> handle(GetAllDeliveryOrdersQuery query) {
        return this.deliveryOrderRepository.findAll();
    }

    @Override
    public boolean handle(DeliveryOrderExistsQuery query) {
        return deliveryOrderRepository.existsById(query.deliveryOrderId());
    }
}
