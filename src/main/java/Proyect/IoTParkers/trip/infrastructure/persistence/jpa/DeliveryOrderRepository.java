package Proyect.IoTParkers.trip.infrastructure.persistence.jpa;

import Proyect.IoTParkers.trip.domain.model.entities.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder,UUID> {
}
