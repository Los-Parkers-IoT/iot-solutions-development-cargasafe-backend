package Proyect.IoTParkers.alerts.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record DeliveryOrderId(Long deliveryOrderId) implements Serializable {
    public DeliveryOrderId {
        if (deliveryOrderId == null || deliveryOrderId <= 0)
            throw new IllegalArgumentException("DeliveryOrderId must be positive");
    }
}

