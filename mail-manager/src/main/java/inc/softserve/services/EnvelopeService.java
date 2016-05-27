package inc.softserve.services;

import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;

public interface EnvelopeService {
    Envelope saveOrUpdate(String subject, String to, String username, String template);
    EnvelopeState getEnvelopeStateById(String id);
}
