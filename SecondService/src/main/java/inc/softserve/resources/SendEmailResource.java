package inc.softserve.resources;

/**
 * Created by Andrew on 06.05.2016.
 */

import inc.softserve.model.Envelope;
import com.codahale.metrics.annotation.Timed;

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
    public String sayHello(Envelope envelope) {
        int a =1;
        String state = "Sent";
        return "{\"state\":\""+state+"\"}";
    }
}
