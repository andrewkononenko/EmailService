package inc.softserve.resources;

import com.google.inject.Inject;
import inc.softserve.EmailServiceApplication;
import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;
import inc.softserve.User;
import inc.softserve.common.EnvelopeTools;
import inc.softserve.annotations.Timed;
import inc.softserve.dao.EnvelopeDao;
import inc.softserve.dao.UserDao;
import inc.softserve.services.EnvelopeService;
import org.hibernate.validator.constraints.NotEmpty;

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

    private EnvelopeService envelopeService;

    @Inject
    public EnvelopeResource(EnvelopeService envelopeService) {
        this.envelopeService = envelopeService;
    }

    @POST
    @Timed
    public Envelope saveEnvelope(@QueryParam("Subject") String subject,
                                 @NotEmpty @QueryParam("To") String to,
                                 @NotEmpty @QueryParam("From") String username,
                                 @NotEmpty @QueryParam("template") String template) {
        return envelopeService.saveOrUpdate(subject, to, username, template);
    }

    @GET
    @Timed
    public EnvelopeState getEnvelopeStateById(@QueryParam("id") String id) {
        return envelopeService.getEnvelopeStateById(id);
    }
}
