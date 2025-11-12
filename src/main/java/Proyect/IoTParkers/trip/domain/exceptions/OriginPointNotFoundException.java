package Proyect.IoTParkers.trip.domain.exceptions;

public class OriginPointNotFoundException extends RuntimeException {
    public OriginPointNotFoundException(Long id) {
        super("OriginPoint with id " + id.toString() + " not found.");
    }
}
