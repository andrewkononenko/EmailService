package inc.softserve;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import inc.softserve.annotations.Configuration;
import inc.softserve.annotations.ESPConnectorAdmin;
import inc.softserve.annotations.ESPConnectorHealth;
import inc.softserve.annotations.ESPConnectorPath;
import inc.softserve.annotations.ESPConnectorUrl;
import inc.softserve.annotations.Timed;
import inc.softserve.common.EnvelopeTools;
import inc.softserve.common.EnvelopeToolsImpl;
import inc.softserve.dao.EnvelopeDao;
import inc.softserve.dao.EnvelopeDaoImpl;
import inc.softserve.dao.MongoManaged;
import inc.softserve.dao.UserDao;
import inc.softserve.dao.UserDaoImpl;
import inc.softserve.interceptors.TimeProcessingInterceptor;
import inc.softserve.services.EnvelopeService;
import inc.softserve.services.EnvelopeServiceImpl;

import java.net.UnknownHostException;

public class EmailServiceModule extends AbstractModule {

    private EmailServiceConfiguration conf;

    public EmailServiceModule(EmailServiceConfiguration conf) {
        this.conf  = conf;
    }

    protected void configure() {
        bind(EmailServiceConfiguration.class).toInstance(conf);
        bind(EnvelopeTools.class).to(EnvelopeToolsImpl.class);
        bind(EnvelopeDao.class).to(EnvelopeDaoImpl.class);
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(EnvelopeService.class).to(EnvelopeServiceImpl.class);
        bind(String.class).annotatedWith(ESPConnectorHealth.class).toInstance(conf.getEspConnectorHealth());
        bind(String.class).annotatedWith(ESPConnectorAdmin.class).toInstance(conf.getEspConnectorAdmin());
        bind(String.class).annotatedWith(ESPConnectorPath.class).toInstance(conf.getSendEmailPath());
        bind(String.class).annotatedWith(ESPConnectorUrl.class).toInstance(conf.getSendEmailUrl());
        bind(MongoConfiguration.class).annotatedWith(Configuration.class).toInstance(conf.getMongoConf());
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Timed.class),new TimeProcessingInterceptor());
        }

    @Provides
    public MongoManaged provideMongoManaged() throws UnknownHostException {
        return new MongoManaged(conf.getMongoConf());
    }
}
