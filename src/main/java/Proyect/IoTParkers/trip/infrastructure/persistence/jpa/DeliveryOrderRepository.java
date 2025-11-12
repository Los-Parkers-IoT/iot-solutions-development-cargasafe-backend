package Proyect.IoTParkers.trip.infrastructure.persistence.jpa;

import Proyect.IoTParkers.trip.domain.model.entities.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
}
