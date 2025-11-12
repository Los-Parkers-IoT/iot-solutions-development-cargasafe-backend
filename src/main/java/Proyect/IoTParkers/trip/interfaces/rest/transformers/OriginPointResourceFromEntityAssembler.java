package Proyect.IoTParkers.trip.interfaces.rest.transformers;


import Proyect.IoTParkers.trip.domain.model.entities.OriginPoint;
import Proyect.IoTParkers.trip.interfaces.rest.resources.OriginPointResource;

public final class OriginPointResourceFromEntityAssembler {
    static public OriginPointResource toResourceFromEntity(OriginPoint entity) {
        return new OriginPointResource(
                entity.getId(),
                entity.getName(),
                entity.getLocation().address(),
                entity.getLocation().latitude(),
                entity.getLocation().longitude()
        );
    }
}
