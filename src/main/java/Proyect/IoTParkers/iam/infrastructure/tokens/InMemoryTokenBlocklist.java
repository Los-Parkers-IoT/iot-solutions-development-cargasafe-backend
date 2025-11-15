package Proyect.IoTParkers.iam.infrastructure.tokens;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryTokenBlocklist {

    private final Map<String, Instant> accessDenylist = new ConcurrentHashMap<>();
    private final Map<String, Instant> revokedRefresh = new ConcurrentHashMap<>();

    public void blockAccess(String jti, Instant exp) {
        accessDenylist.put(jti, exp);
    }
    public boolean isAccessBlocked(String jti) {
        Instant exp = accessDenylist.get(jti);
        if (exp == null) return false;
        if (exp.isBefore(Instant.now())) { accessDenylist.remove(jti); return false; }
        return true;
    }

    public void revokeRefresh(String jti, Instant exp) {
        revokedRefresh.put(jti, exp);
    }
    public boolean isRefreshRevoked(String jti) {
        Instant exp = revokedRefresh.get(jti);
        if (exp == null) return false;
        if (exp.isBefore(Instant.now())) { revokedRefresh.remove(jti); return false; }
        return true;
    }
}