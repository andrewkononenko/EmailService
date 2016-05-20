package inc.softserve.dao;

import inc.softserve.Envelope;

public interface EnvelopeDao {
    String saveOrUpdate(Envelope e);
    Envelope getById(String id);
}
