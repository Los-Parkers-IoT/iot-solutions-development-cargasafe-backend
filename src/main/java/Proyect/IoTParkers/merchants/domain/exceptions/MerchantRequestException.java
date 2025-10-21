package Proyect.IoTParkers.merchants.domain.exceptions;

public class MerchantRequestException extends RuntimeException {
    public MerchantRequestException(String exceptionMessage) {
        super("Error while creating merchant: %s".formatted(exceptionMessage));
    }
}
