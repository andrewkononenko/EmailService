package inc.softserve.health;

import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.DB;
import inc.softserve.annotations.ESPConnectorAdmin;
import inc.softserve.annotations.ESPConnectorHealth;
import inc.softserve.common.EnvelopeTools;
import inc.softserve.dao.MongoManaged;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class MailManagerHealthCheck extends HealthCheck{

    private String espConnectorAdminUrl;
    private String espConnectorHealthPath;
    private EnvelopeTools envelopeTools;
    private MongoManaged mongo;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public MailManagerHealthCheck(@ESPConnectorAdmin String espConnectorAdminUrl,
                                  @ESPConnectorHealth String espConnectorHealthPath,
                                  EnvelopeTools envelopeTools, MongoManaged mongo){
        this.espConnectorAdminUrl = espConnectorAdminUrl;
        this.espConnectorHealthPath = espConnectorHealthPath;
        this.envelopeTools = envelopeTools;
        this.mongo = mongo;
    }

    protected Result check() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        FutureTask<Boolean> espConnectorCheck = new FutureTask<>(this::isEspConnectorHealthy);
        FutureTask<Boolean> mongoCheck = new FutureTask<>(this::isMongoHealthy);

        FutureTask<Boolean> commonHealth = new FutureTask<>(() -> {
            executor.execute(espConnectorCheck);
            executor.execute(mongoCheck);
            return espConnectorCheck.get() && mongoCheck.get();
        });

        try {
            executor.execute(commonHealth);
            if (commonHealth.get(500L, TimeUnit.MILLISECONDS)) {
                return Result.healthy();
            }
        } catch (Exception e) {
            // Just unhealthy
        }
        return Result.unhealthy("Service unavailable!");
    }

    private boolean isEspConnectorHealthy(){
        try {
            String responseAsString = envelopeTools.sendGetRequest(espConnectorAdminUrl, espConnectorHealthPath);
            JsonNode responseAsNode = objectMapper.readValue(responseAsString, JsonNode.class);
            return responseAsNode.get("health").get("healthy").asBoolean();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isMongoHealthy(){
        DB db = mongo.getDb();
        db.getStats();
        return true;
    }
}
