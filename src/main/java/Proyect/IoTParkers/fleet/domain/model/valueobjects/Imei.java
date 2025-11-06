package Proyect.IoTParkers.fleet.domain.model.valueobjects;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;
import jakarta.persistence.Embeddable;

@Embeddable
public record Imei(String value) {
    
    public Imei {
        value = Preconditions.requireNonBlank(value, "IMEI");
        value = value.trim();
        
        if (!value.matches("IMEI-[0-9]{7,15}")) {
            throw new IllegalArgumentException("IMEI must match pattern IMEI-[0-9]{7,15}");
        }
    }
}
