package inc.softserve.common;

import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;

import java.io.IOException;

public interface EnvelopeTools {
    EnvelopeState sendEnvelope(Envelope envelope) throws Exception;

    String sendGetRequest(String url, String path);
}

