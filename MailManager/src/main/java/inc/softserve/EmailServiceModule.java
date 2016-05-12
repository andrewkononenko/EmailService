package inc.softserve;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import inc.softserve.common.EnvelopeTools;
import inc.softserve.common.EnvelopeToolsImpl;
import inc.softserve.dao.EnvelopeDao;
import inc.softserve.dao.EnvelopeDaoImpl;

/**
 * Created by Andrew on 12.05.2016.
 */
public class EmailServiceModule extends AbstractModule {
    protected void configure() {
        bind(EnvelopeTools.class).to(EnvelopeToolsImpl.class);
        bind(EnvelopeDao.class).to(EnvelopeDaoImpl.class);
    }

    @Provides
    @Named("sendEmailUrl")
    public String provideUrl(EmailServiceConfiguration configuration) {
        return configuration.getSendEmailUrl();
    }

    @Provides
    @Named("sendEmailPath")
    public String providePath(EmailServiceConfiguration configuration) {
        return configuration.getSendEmailPath();
    }
}
