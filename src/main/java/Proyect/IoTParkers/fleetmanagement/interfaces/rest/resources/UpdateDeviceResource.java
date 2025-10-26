package Proyect.IoTParkers.fleetmanagement.interfaces.rest.resources;

public record UpdateDeviceResource(
        String type,
        String firmware,
        Boolean online
) {
}
