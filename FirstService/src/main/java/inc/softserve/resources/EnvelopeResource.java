package inc.softserve.resources;

/**
 * Created by Andrew on 10.05.2016.
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inc.softserve.dao.EnvelopeDao;
import inc.softserve.dao.EnvelopeDaoImpl;
import inc.softserve.model.Envelope;
import inc.softserve.model.EnvelopeState;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/envelope")
@Produces(MediaType.APPLICATION_JSON)
public class EnvelopeResource {

    private EnvelopeDao dao = new EnvelopeDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @POST
    public long saveEnvelope(@QueryParam("Subject") String subject,
                               @QueryParam("To") String to,
                               @QueryParam("From") String username,
                               @QueryParam("template") String template,
                               @Context HttpServletRequest request){
        Envelope envelope = new Envelope(username, to, subject, template);
        dao.saveOrUpdate(envelope);
        EnvelopeState sendState = null;
        try {
            sendState = sendEnvelope(envelope);
        }catch(IOException e){
            e.printStackTrace();
            sendState = EnvelopeState.ERROR;
        }

        if(!"In progress".equals(sendState)){
            envelope.setState(sendState);
            dao.saveOrUpdate(envelope);
        }

        return envelope.getId();
    }

    @GET
    public EnvelopeState getEnvelopeStateById(@QueryParam("id") long id){
        Envelope envelope = dao.getById(id);
        return envelope.getState();
    }

    private EnvelopeState sendEnvelope(Envelope envelope) throws IOException{
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:7080").path("send");
        String envelopeJson = mapper.writeValueAsString(envelope);

        Response sendEnvResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                        .post(Entity.entity(envelopeJson,MediaType.APPLICATION_JSON_TYPE));

        String responseAsString = sendEnvResponse.readEntity(String.class);
        JsonNode jsonNode = mapper.readValue(responseAsString,JsonNode.class);
        EnvelopeState state = EnvelopeState.getNameByCode(jsonNode.get("state").asText());
        return state;
    }
}
