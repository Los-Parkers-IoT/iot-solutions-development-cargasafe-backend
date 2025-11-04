package Proyect.IoTParkers.trip.application.internal.commandservices;

import Proyect.IoTParkers.trip.domain.model.aggregates.Trip;
import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import Proyect.IoTParkers.trip.domain.services.TripCommandService;
import Proyect.IoTParkers.trip.infrastructure.persistence.jpa.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TripCommandServiceImpl implements TripCommandService {

    private final TripRepository tripRepository;

    @Value("${integrations.merchants.base-url:http://localhost:8080}")
    private String merchantsBaseUrl;

    private final java.net.http.HttpClient http = java.net.http.HttpClient.newHttpClient();

    @Override
    public Trip create(Long merchantId, Instant createdAtOverride) {
        assertMerchantExists(merchantId);

        Trip t = new Trip(merchantId);

        if (createdAtOverride != null) {
            try {
                var createdAtField = Trip.class.getDeclaredField("createdAt");
                createdAtField.setAccessible(true);
                createdAtField.set(t, createdAtOverride);
            } catch (Exception ignored) {}
        }
        return tripRepository.save(t);
    }

    @Override
    public Optional<Trip> updateStatus(UUID tripId, TripStatus status, Instant startedAtOv, Instant completedAtOv) {
        return tripRepository.findById(tripId).map(t -> {
            switch (status) {
                case CREATED -> { /* no-op */ }
                case IN_PROGRESS -> {
                    if (t.getStartedAt() == null) {
                        setField(t, "startedAt", startedAtOv != null ? startedAtOv : Instant.now());
                    }
                }
                case COMPLETED -> {
                    if (t.getStartedAt() == null) {
                        setField(t, "startedAt", startedAtOv != null ? startedAtOv : Instant.now());
                    }
                    setField(t, "completedAt", completedAtOv != null ? completedAtOv : Instant.now());
                }
                case CANCELED -> { /* no timestamps required */ }
            }
            setField(t, "status", status);
            tripRepository.save(t);
            return t;
        });
    }

    private static void setField(Trip t, String name, Object value) {
        try {
            var f = Trip.class.getDeclaredField(name);
            f.setAccessible(true);
            f.set(t, value);
        } catch (Exception ignored) {}
    }


    private void assertMerchantExists(Long merchantId) {
        try {
            String bearer = null;
            var attrs = org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
            if (attrs instanceof org.springframework.web.context.request.ServletRequestAttributes sra) {
                bearer = sra.getRequest().getHeader("Authorization"); // "Bearer xxx"
            }

            var reqBuilder = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(merchantsBaseUrl + "/api/v1/merchants/" + merchantId))
                    .GET();

            if (bearer != null && !bearer.isBlank()) {
                reqBuilder.header("Authorization", bearer);
            }

            var req = reqBuilder.build();
            var res = http.send(req, java.net.http.HttpResponse.BodyHandlers.discarding());

            if (res.statusCode() == 404) {
                throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Merchant not found");
            }
            if (res.statusCode() < 200 || res.statusCode() >= 300) {
                throw new ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_GATEWAY,
                        "Merchants service error: HTTP " + res.statusCode()
                );
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Unable to reach Merchants service",
                    e
            );
        }
    }
}
