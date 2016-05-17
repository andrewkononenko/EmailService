package inc.softserve.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;
import inc.softserve.annotations.ESPConnectorPath;
import inc.softserve.annotations.ESPConnectorUrl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class EnvelopeToolsImpl implements EnvelopeTools{

    private String sendEnvelopeServiceUrl;
    private String sendEnvelopeServicePath;
    private ObjectMapper mapper;
    Client client = ClientBuilder.newClient();

    @Inject
    public EnvelopeToolsImpl(@ESPConnectorUrl String sendEnvelopeServiceUrl,
                             @ESPConnectorPath String sendEnvelopeServicePath,
                             ObjectMapper mapper) {
        this.sendEnvelopeServiceUrl = sendEnvelopeServiceUrl;
        this.sendEnvelopeServicePath = sendEnvelopeServicePath;
        this.mapper = mapper;
    }

    public EnvelopeState sendEnvelope(Envelope envelope) throws IOException {
        WebTarget target = client.target(sendEnvelopeServiceUrl).path(sendEnvelopeServicePath);
        String envelopeJson = mapper.writeValueAsString(envelope);

        Response sendEnvResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(envelopeJson,MediaType.APPLICATION_JSON_TYPE));

        String responseAsString = sendEnvResponse.readEntity(String.class);
        EnvelopeState state = mapper.readValue(responseAsString,EnvelopeState.class);
        return state;
    }

    public String sendGetRequest(String url, String path){
        WebTarget target = client.target(url).path(path);
        Response sendEnvResponse = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        String responseAsString = sendEnvResponse.readEntity(String.class);

        return responseAsString;
    }
}
