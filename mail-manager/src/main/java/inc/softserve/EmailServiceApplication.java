package inc.softserve;

import com.google.inject.Guice;
import com.google.inject.Injector;
import inc.softserve.health.MailManagerHealthCheck;
import inc.softserve.resources.EnvelopeResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class EmailServiceApplication extends Application<EmailServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new EmailServiceApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<EmailServiceConfiguration> bootstrap) {
    }

    @Override
    public void run(EmailServiceConfiguration configuration,
                    Environment environment) {
        Injector injector = Guice.createInjector(new EmailServiceModule(configuration));
        environment.jersey().register(injector.getInstance(EnvelopeResource.class));
        environment.healthChecks().register("health", injector.getInstance(MailManagerHealthCheck.class));
    }
}
