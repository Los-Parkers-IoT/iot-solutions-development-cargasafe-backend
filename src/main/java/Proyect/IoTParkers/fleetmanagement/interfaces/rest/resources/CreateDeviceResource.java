package Proyect.IoTParkers.fleetmanagement.interfaces.rest.resources;

public record CreateDeviceResource(
        String imei,
        String type,
        String firmware,
        Boolean online,
        String vehiclePlate
) {
}
