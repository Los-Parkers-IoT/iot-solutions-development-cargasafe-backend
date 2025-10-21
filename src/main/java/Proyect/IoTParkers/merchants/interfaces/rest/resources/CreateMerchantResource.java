package Proyect.IoTParkers.merchants.interfaces.rest.resources;

public record CreateMerchantResource(
        String name,
        String contactEmail,
        String fiscalAddress,
        String ruc
) {
}
