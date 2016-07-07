package inc.softserve.dao;

import inc.softserve.Envelope;

public interface EnvelopeDao {
    Envelope saveOrUpdate(Envelope e);

    Envelope getById(String id);
}
