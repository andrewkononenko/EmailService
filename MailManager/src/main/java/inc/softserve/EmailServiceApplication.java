package inc.softserve;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.HashMap;
import java.util.Map;

public class EmailServiceApplication extends Application<EmailServiceConfiguration> {

    static public long id;
    static public Map<Long, Envelope> envolopes = new HashMap<Long, Envelope>();
    static public Map<String, User> users = new HashMap<String, User>();

    static {
        users.put("akonon", new User("akonon", "a", "b"));
        users.put("akonon2", new User("akonon2", "a", "b"));
        users.put("akonon3", new User("akonon3", "a", "b"));
        users.put("akonon4", new User("akonon4", "a", "b"));
    }

    public static void main(String[] args) throws Exception {
        new EmailServiceApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<EmailServiceConfiguration> bootstrap) {

        GuiceBundle<EmailServiceConfiguration> guiceBundle = GuiceBundle.<EmailServiceConfiguration>newBuilder()
                .addModule(new EmailServiceModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(EmailServiceConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(EmailServiceConfiguration configuration,
                    Environment environment) {

    }
}
