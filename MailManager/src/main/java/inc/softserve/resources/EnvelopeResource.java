package inc.softserve.resources;

import com.google.inject.Inject;
import inc.softserve.EmailServiceApplication;
import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;
import inc.softserve.common.EnvelopeTools;
import inc.softserve.annotations.Timed;
import inc.softserve.dao.EnvelopeDao;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/envelope")
@Produces(MediaType.APPLICATION_JSON)
public class EnvelopeResource {

    private EnvelopeDao dao;
    private EnvelopeTools envelopeTools;

    @Inject
    public EnvelopeResource(EnvelopeDao dao, EnvelopeTools envelopeTools){
        this.dao = dao;
        this.envelopeTools = envelopeTools;
    }

    @POST
    @Timed
    public long saveEnvelope(@QueryParam("Subject") String subject,
                               @QueryParam("To") String to,
                               @QueryParam("From") String username,
                               @QueryParam("template") String template,
                               @Context HttpServletRequest request){
        Envelope envelope = new Envelope(EmailServiceApplication.users.get(username),
                                            to, subject, template, EnvelopeState.IN_PROGRESS);
        dao.saveOrUpdate(envelope);
        EnvelopeState sendState = null;
        try {
            sendState = envelopeTools.sendEnvelope(envelope);
        }catch(Exception e){
            e.printStackTrace();
            sendState = EnvelopeState.ERROR;
        }

        if(!EnvelopeState.IN_PROGRESS.equals(sendState)){
            envelope.setState(sendState);
            dao.saveOrUpdate(envelope);
        }

        return envelope.getId();
    }

    @GET
    @Timed
    public EnvelopeState getEnvelopeStateById(@QueryParam("id") long id){
        Envelope envelope = dao.getById(id);
        if(envelope != null) {
            System.out.println("Method ended");
            return envelope.getState();
        }
        return EnvelopeState.NOT_FOUND;
    }
}
