package Proyect.IoTParkers.fleet.domain.exceptions;

public class DeviceAlreadyAssignedException extends RuntimeException {
    public DeviceAlreadyAssignedException(String imei) {
        super("Device with IMEI " + imei + " is already assigned to another vehicle");
    }
}
