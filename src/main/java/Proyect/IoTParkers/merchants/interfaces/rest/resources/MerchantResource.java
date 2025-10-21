package Proyect.IoTParkers.merchants.interfaces.rest.resources;

public record MerchantResource(Long id, String name, String contactEmail, String fiscalAddress, String ruc,
                               boolean iActive) {
}
