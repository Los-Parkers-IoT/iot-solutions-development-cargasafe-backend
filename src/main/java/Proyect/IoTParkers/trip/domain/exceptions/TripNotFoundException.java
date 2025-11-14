package Proyect.IoTParkers.trip.domain.exceptions;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(Long tripId) {
        super("Trip with id " + tripId.toString() + " not found.");
    }
}
