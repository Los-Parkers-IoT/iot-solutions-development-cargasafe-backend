package Proyect.IoTParkers.fleet.interfaces.rest.resources;

public record CreateDeviceResource(
        String imei,
        String firmware,
        Boolean online
) {
}
