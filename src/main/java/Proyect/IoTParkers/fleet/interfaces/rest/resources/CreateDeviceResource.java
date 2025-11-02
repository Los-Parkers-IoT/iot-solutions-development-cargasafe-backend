package Proyect.IoTParkers.fleet.interfaces.rest.resources;

public record CreateDeviceResource(
        String imei,
        String type,
        String firmware,
        Boolean online
) {
}
