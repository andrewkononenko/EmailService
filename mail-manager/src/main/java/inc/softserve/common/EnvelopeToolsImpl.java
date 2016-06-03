package inc.softserve.common;

import com.google.inject.Inject;
import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class EnvelopeToolsImpl implements EnvelopeTools {

    private Client client = ClientBuilder.newClient();
    private EnvelopeQueueProducer producer;

    @Inject
    public EnvelopeToolsImpl(EnvelopeQueueProducer producer) {
        this.producer = producer;
    }

    public EnvelopeState sendEnvelope(Envelope envelope) throws Exception {
        return producer.send(envelope);
    }

    public String sendGetRequest(String url, String path) {
        WebTarget target = client.target(url).path(path);
        Response sendEnvResponse = target.request(MediaType.APPLICATION_JSON_TYPE).get();

        return sendEnvResponse.readEntity(String.class);
    }
}
