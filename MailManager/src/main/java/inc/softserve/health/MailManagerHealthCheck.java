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

import java.io.IOException;

public class MailManagerHealthCheck extends HealthCheck{

    private String espConnectorAdminUrl;
    private String espConnectorHealthPath;
    private EnvelopeTools envelopeTools;
    private MongoManaged mongo;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public MailManagerHealthCheck(@ESPConnectorAdmin String ESPConnectorAdminUrl,
                                  @ESPConnectorHealth String ESPConnectorHealthPath,
                                  EnvelopeTools envelopeTools, MongoManaged mongo){
        this.espConnectorAdminUrl = ESPConnectorAdminUrl;
        this.espConnectorHealthPath = ESPConnectorHealthPath;
        this.envelopeTools = envelopeTools;
        this.mongo = mongo;
    }

    protected Result check() throws Exception {
        boolean espConnectorHealthy = false;
        StringBuilder unhealthyMessage = new StringBuilder("");

        try {
            String responseAsString = envelopeTools.sendGetRequest(espConnectorAdminUrl, espConnectorHealthPath);
            JsonNode responseAsNode = objectMapper.readValue(responseAsString, JsonNode.class);
            espConnectorHealthy = responseAsNode.get("health").get("healthy").asBoolean();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!espConnectorHealthy) {
            unhealthyMessage = unhealthyMessage.append("ESPConnector is unavailable!");
        }

        try {
            DB db = mongo.getDb();
            db.getStats();
        } catch (Exception e) {
            unhealthyMessage = unhealthyMessage.append(" Mongo DB is unavailable!");
        }

        if (unhealthyMessage.length() != 0) {
            return Result.unhealthy(unhealthyMessage.toString());
        }

        return Result.healthy();
    }
}
