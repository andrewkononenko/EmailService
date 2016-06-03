package inc.softserve.services;

import com.google.inject.Inject;
import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;
import inc.softserve.User;
import inc.softserve.common.EnvelopeTools;
import inc.softserve.dao.EnvelopeDao;
import inc.softserve.dao.UserDao;

public class EnvelopeServiceImpl implements EnvelopeService{

    private EnvelopeDao envelopeDao;
    private UserDao userDao;
    private EnvelopeTools envelopeTools;

    @Inject
    public EnvelopeServiceImpl(EnvelopeDao envelopeDao, UserDao userDao, EnvelopeTools envelopeTools) {
        this.envelopeDao = envelopeDao;
        this.userDao = userDao;
        this.envelopeTools = envelopeTools;
    }

    public Envelope saveOrUpdate(String subject, String to, String username, String template) {

        User from = userDao.getUserByUsername(username);

        if(from == null) {
            throw new IllegalArgumentException("No such user registered");
        }

        Envelope envelope = new Envelope(from, to, subject, template, EnvelopeState.IN_PROGRESS);
        Envelope savedEnvelope = envelopeDao.saveOrUpdate(envelope);
        EnvelopeState sendState;
        try {
            sendState = envelopeTools.sendEnvelope(savedEnvelope);
        } catch (Exception e) {
            e.printStackTrace();
            sendState = EnvelopeState.ERROR;
        }

        savedEnvelope.setState(sendState);
        envelopeDao.saveOrUpdate(savedEnvelope);
        return savedEnvelope;
    }

    public EnvelopeState getEnvelopeStateById(String id) {
        Envelope envelope = envelopeDao.getById(id);
        if (envelope != null) {
            return envelope.getState();
        }

        return EnvelopeState.NOT_FOUND;
    }
}
