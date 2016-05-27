package inc.softserve.dao;

import com.google.inject.Inject;
import inc.softserve.Envelope;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import static org.mongojack.JacksonDBCollection.wrap;

public class EnvelopeDaoImpl implements EnvelopeDao {

    private MongoManaged mongo;
    private JacksonDBCollection<Envelope, String> envelopes;

    @Inject
    public EnvelopeDaoImpl(MongoManaged mongo) {
        this.mongo = mongo;
        envelopes = wrap(mongo.getDb().getCollection("envelopes"), Envelope.class, String.class);
    }

    public Envelope saveOrUpdate(Envelope envelope) {
        WriteResult<Envelope, String> writeResult = envelopes.save(envelope);
        Envelope savedEnvelope = writeResult.getSavedObject();
        return savedEnvelope;
    }

    public Envelope getById(String id) {
        try {
            DBCursor<Envelope> cursor = envelopes.find().is("_id", id);
            if (cursor.hasNext()) {
                return cursor.next();
            }
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
