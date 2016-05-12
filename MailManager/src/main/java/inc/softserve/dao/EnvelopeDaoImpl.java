package inc.softserve.dao;

import inc.softserve.EmailServiceApplication;
import inc.softserve.Envelope;

public class EnvelopeDaoImpl implements EnvelopeDao {
    public long saveOrUpdate(Envelope envelope) {
        if(!EmailServiceApplication.envolopes.containsKey(envelope.getId()))
            envelope.setId(++EmailServiceApplication.id);
        EmailServiceApplication.envolopes.put(envelope.getId(), envelope);
        return envelope.getId();
    }

    public Envelope getById(long id) {
        return EmailServiceApplication.envolopes.get(id);
    }
}
