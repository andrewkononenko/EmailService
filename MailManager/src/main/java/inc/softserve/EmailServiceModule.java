package inc.softserve;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Named;
import inc.softserve.annotations.*;
import inc.softserve.common.*;
import inc.softserve.dao.EnvelopeDao;
import inc.softserve.dao.EnvelopeDaoImpl;
import inc.softserve.interceptors.TimeProcessingInterceptor;

public class EmailServiceModule extends AbstractModule {

    EmailServiceConfiguration conf;

    public EmailServiceModule(EmailServiceConfiguration conf) {
        this.conf  = conf;
    }

    protected void configure() {
        bind(EmailServiceConfiguration.class).toInstance(conf);
        bind(EnvelopeTools.class).to(EnvelopeToolsImpl.class);
        bind(EnvelopeDao.class).to(EnvelopeDaoImpl.class);
        bind(String.class).annotatedWith(ESPConnectorHealth.class).toInstance(conf.ESPConnectorHealth);
        bind(String.class).annotatedWith(ESPConnectorAdmin.class).toInstance(conf.ESPConnectorAdmin);
        bind(String.class).annotatedWith(ESPConnectorPath.class).toInstance(conf.sendEmailPath);
        bind(String.class).annotatedWith(ESPConnectorUrl.class).toInstance(conf.sendEmailUrl);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Timed.class),new TimeProcessingInterceptor());
    }
}
