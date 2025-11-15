package Proyect.IoTParkers.shared.infrastructure.security;

public final class PasswordPatterns {
    private PasswordPatterns() {}

    // ≥8, 1 minúscula, 1 mayúscula, 1 especial (no alfanumérico)
    public static final String STRONG_PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$";
}