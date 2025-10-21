package Proyect.IoTParkers.merchants.domain.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Employee with id %s not found".formatted(id));
    }
}
