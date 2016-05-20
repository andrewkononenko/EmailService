package inc.softserve.resources;

import com.google.inject.Inject;
import inc.softserve.EmailServiceApplication;
import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;
import inc.softserve.common.EnvelopeTools;
import inc.softserve.annotations.Timed;
import inc.softserve.dao.EnvelopeDao;
import inc.softserve.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/envelope")
@Produces(MediaType.APPLICATION_JSON)
public class EnvelopeResource {

    private EnvelopeDao envelopeDao;
    private UserDao userDao;
    private EnvelopeTools envelopeTools;

    @Inject
    public EnvelopeResource(EnvelopeDao envelopeDao, UserDao userDao, EnvelopeTools envelopeTools){
        this.envelopeDao = envelopeDao;
        this.userDao = userDao;
        this.envelopeTools = envelopeTools;
    }

    @POST
    @Timed
    public String saveEnvelope(@QueryParam("Subject") String subject,
                               @QueryParam("To") String to,
                               @QueryParam("From") String username,
                               @QueryParam("template") String template,
                               @Context HttpServletRequest request){
        Envelope envelope = new Envelope(userDao.getUserByUsername(username),
                                            to, subject, template, EnvelopeState.IN_PROGRESS);
        String id = envelopeDao.saveOrUpdate(envelope);
        envelope.setId(id);
        EnvelopeState sendState = null;
        try {
            sendState = envelopeTools.sendEnvelope(envelope);
        }catch(Exception e){
            e.printStackTrace();
            sendState = EnvelopeState.ERROR;
        }

        if(!EnvelopeState.IN_PROGRESS.equals(sendState)){
            envelope.setState(sendState);
            envelopeDao.saveOrUpdate(envelope);
        }

        return "\"_id\":"+"\""+envelope.getId()+"\"";
    }

    @GET
    @Timed
    public EnvelopeState getEnvelopeStateById(@QueryParam("id") String id){
        Envelope envelope = envelopeDao.getById(id);
        if(envelope != null) {
            return envelope.getState();
        }
        return EnvelopeState.NOT_FOUND;
    }
}
