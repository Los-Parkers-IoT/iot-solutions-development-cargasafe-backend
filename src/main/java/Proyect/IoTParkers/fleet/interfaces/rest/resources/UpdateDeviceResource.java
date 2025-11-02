package Proyect.IoTParkers.fleet.interfaces.rest.resources;

public record UpdateDeviceResource(
        String type,
        String firmware,
        Boolean online
) {
}
