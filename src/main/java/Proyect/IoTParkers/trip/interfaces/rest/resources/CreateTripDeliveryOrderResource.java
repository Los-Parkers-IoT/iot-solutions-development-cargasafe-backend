package Proyect.IoTParkers.trip.interfaces.rest.resources;

public record CreateTripDeliveryOrderResource(String clientEmail, String address, Double latitude, Double longitude,
                                              Long sequenceOrder, Double maxHumidity, Double minHumidity,
                                              Double maxTemperature, Double minTemperature, Double maxVibration) {
    
}
