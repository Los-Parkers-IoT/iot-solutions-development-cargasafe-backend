package Proyect.IoTParkers.trip.interfaces.acl.resources;

public record AclTripThresholdResource(Double minTemperature, Double maxTemperature, Double minHumidity,
                                       Double maxHumidity, Double maxVibration) {
}
