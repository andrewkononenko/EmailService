package inc.softserve.resources;

/**
 * Created by Andrew on 10.05.2016.
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inc.softserve.dao.EnvelopeDao;
import inc.softserve.dao.EnvelopeDaoImpl;
import inc.softserve.model.Envelope;

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
        String sendState = null;
        try {
            sendState = sendEnvelope(envelope);
        }catch(IOException e){
            e.printStackTrace();
            sendState = "Not sent, due to error!";
        }

        if(!"In progress".equals(sendState)){
            envelope.setState(sendState);
            dao.saveOrUpdate(envelope);
        }

        return envelope.getId();
    }

    @GET
    public String getEnvelopeStateById(@QueryParam("id") long id){
        Envelope envelope = dao.getById(id);
        return "{\"state\":\""+envelope.getState()+"\"}";
    }

    private String sendEnvelope(Envelope envelope) throws IOException{
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:7080").path("hello-world");
        String envelopeJson = mapper.writeValueAsString(envelope);

        Response sendEnvResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                        .post(Entity.entity(envelopeJson,MediaType.APPLICATION_JSON_TYPE));

        String responseAsString = sendEnvResponse.readEntity(String.class);
        JsonNode jsonNode = mapper.readValue(responseAsString,JsonNode.class);
        return jsonNode.get("state").asText();
    }
}
