package Proyect.IoTParkers.fleet.interfaces.rest.resources;

public record UpdateDeviceResource(
        String firmware,
        Boolean online
) {
}
