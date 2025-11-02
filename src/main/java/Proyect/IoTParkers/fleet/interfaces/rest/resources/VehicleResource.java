package Proyect.IoTParkers.fleet.interfaces.rest.resources;

import java.util.Set;

public record VehicleResource(
        Long id,
        String plate,
        String type,
        Set<String> capabilities,
        String status,
        Integer odometerKm,
        String deviceImei
) {
}
