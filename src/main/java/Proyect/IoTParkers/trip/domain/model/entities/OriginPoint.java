package Proyect.IoTParkers.trip.domain.model.entities;

import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import Proyect.IoTParkers.trip.domain.model.commands.CreateOriginPointCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class OriginPoint extends AuditableModel {
    private String name;
    @Column(columnDefinition = "TEXT")
    private String address;
    private Double latitude;
    private Double longitude;


    public OriginPoint(CreateOriginPointCommand command) {
        this.name = command.name();
        this.address = command.address();
        this.latitude = command.latitude();
        this.longitude = command.longitude();
    }
}
