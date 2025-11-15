package Proyect.IoTParkers.iam.interfaces.rest.resources;

public record RevokeRequest(String refreshToken, Boolean allDevices) {}