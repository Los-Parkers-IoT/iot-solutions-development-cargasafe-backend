package Proyect.IoTParkers.merchants.domain.exceptions;

public class MerchantNotFoundException extends RuntimeException {
    public MerchantNotFoundException(Long id) {
        super("Merchant with id %s not found".formatted(id));
    }
}
