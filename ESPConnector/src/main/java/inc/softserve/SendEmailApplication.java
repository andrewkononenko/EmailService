package inc.softserve;

import inc.softserve.resources.SendEmailResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SendEmailApplication extends Application<SendEmailConfiguration> {
    public static void main(String[] args) throws Exception {
        new SendEmailApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<SendEmailConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(SendEmailConfiguration configuration,
                    Environment environment) {
        final SendEmailResource resource = new SendEmailResource();
        environment.jersey().register(resource);
    }

}