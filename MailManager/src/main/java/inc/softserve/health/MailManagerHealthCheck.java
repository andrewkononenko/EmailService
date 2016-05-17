package inc.softserve.health;

import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import inc.softserve.annotations.ESPConnectorAdmin;
import inc.softserve.annotations.ESPConnectorHealth;
import inc.softserve.common.EnvelopeTools;

import java.io.IOException;

public class MailManagerHealthCheck extends HealthCheck{

    String ESPConnectorAdminUrl;
    String ESPConnectorHealthPath;
    private EnvelopeTools envelopeTools;
    ObjectMapper objectMapper = new ObjectMapper();


    @Inject
    public MailManagerHealthCheck(@ESPConnectorAdmin String ESPConnectorAdminUrl,
                                  @ESPConnectorHealth String ESPConnectorHealthPath,
                                  EnvelopeTools envelopeTools){
        this.ESPConnectorAdminUrl = ESPConnectorAdminUrl;
        this.ESPConnectorHealthPath = ESPConnectorHealthPath;
        this.envelopeTools = envelopeTools;
    }

    protected Result check() throws Exception {
        String responseAsString = envelopeTools.sendGetRequest(ESPConnectorAdminUrl, ESPConnectorHealthPath);
        boolean espConnectorHealthy = false;
        try {
            JsonNode responseAsNode = objectMapper.readValue(responseAsString, JsonNode.class);
            espConnectorHealthy = responseAsNode.get("health").get("healthy").asBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (espConnectorHealthy)
            return Result.healthy();

        return Result.unhealthy("ESPConnector is unavailable!");
    }
}
