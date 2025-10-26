package Proyect.IoTParkers.fleetmanagement.domain.exceptions;

public class DeviceAssignmentConflictException extends RuntimeException {
    public DeviceAssignmentConflictException(String message) {
        super(message);
    }
}
