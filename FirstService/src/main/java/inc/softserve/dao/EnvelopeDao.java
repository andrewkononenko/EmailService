package inc.softserve.dao;

import inc.softserve.model.Envelope;

/**
 * Created by Andrew on 10.05.2016.
 */
public interface EnvelopeDao {
    long saveOrUpdate(Envelope e);
    Envelope getById(long id);
}
