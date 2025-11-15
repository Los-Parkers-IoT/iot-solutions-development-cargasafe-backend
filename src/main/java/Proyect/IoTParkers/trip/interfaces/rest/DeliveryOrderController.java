package Proyect.IoTParkers.trip.interfaces.rest;


import Proyect.IoTParkers.trip.domain.exceptions.DeliveryOrderNotFoundException;
import Proyect.IoTParkers.trip.domain.exceptions.TripNotFoundException;
import Proyect.IoTParkers.trip.domain.model.commands.DeliverDeliveryOrderCommand;
import Proyect.IoTParkers.trip.domain.model.queries.GetAllDeliveryOrdersQuery;
import Proyect.IoTParkers.trip.domain.services.DeliveryOrderCommandService;
import Proyect.IoTParkers.trip.domain.services.DeliveryOrderQueryService;
import Proyect.IoTParkers.trip.interfaces.rest.resources.DeliveryOrderResource;
import Proyect.IoTParkers.trip.interfaces.rest.transformers.DeliveryOrderResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/delivery-orders")
@Tag(name = "Delivery Orders", description = "Endpoint for managing delivery orders")
public class DeliveryOrderController {
    @Autowired
    private DeliveryOrderQueryService deliveryOrderQueryService;
    @Autowired
    private DeliveryOrderCommandService deliveryOrderCommandService;


    @GetMapping
    public ResponseEntity<List<DeliveryOrderResource>> getAll() {
        var query = new GetAllDeliveryOrdersQuery();

        var deliveryOrders = deliveryOrderQueryService.handle(query)
                .stream()
                .map(DeliveryOrderResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(deliveryOrders);
    }

    @PostMapping("/{id}/delivery")
    public ResponseEntity markAsDelivered(@PathVariable Long id) {
        try {
            System.out.println("Marking delivery order as delivered: " + id);
            var command = new DeliverDeliveryOrderCommand(id);
            deliveryOrderCommandService.handle(command);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e instanceof DeliveryOrderNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (e instanceof TripNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            throw new RuntimeException(e);
        }
    }
}
