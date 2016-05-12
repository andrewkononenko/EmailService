package inc.softserve.dao;

import inc.softserve.Envelope;

public interface EnvelopeDao {
    long saveOrUpdate(Envelope e);
    Envelope getById(long id);
}
