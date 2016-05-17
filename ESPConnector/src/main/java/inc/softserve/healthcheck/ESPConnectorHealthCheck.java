package inc.softserve.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class ESPConnectorHealthCheck extends HealthCheck {
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
