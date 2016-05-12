package inc.softserve.resources;

import com.codahale.metrics.annotation.Timed;
import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/send")
@Produces(MediaType.APPLICATION_JSON)
public class SendEmailResource {

    public SendEmailResource() {
    }

    @POST
    @Timed
    public EnvelopeState sayHello(Envelope envelope) {
        int a =1;
        String state = "Sent";
        return EnvelopeState.SENT;
    }
}
