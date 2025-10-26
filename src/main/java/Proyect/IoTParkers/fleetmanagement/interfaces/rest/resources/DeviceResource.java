package Proyect.IoTParkers.fleetmanagement.interfaces.rest.resources;

public record DeviceResource(
        Long id,
        String imei,
        String type,
        String firmware,
        boolean online,
        String vehiclePlate
) {
}
