package inc.softserve.dao;

import com.google.inject.Inject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import inc.softserve.MongoConfiguration;
import inc.softserve.annotations.Configuration;
import io.dropwizard.lifecycle.Managed;

import java.net.UnknownHostException;

public class MongoManaged implements Managed {

    private MongoClient mongo;
    private DB db;

    @Inject
    public MongoManaged(@Configuration MongoConfiguration configuration) throws UnknownHostException {
        this.mongo = new MongoClient(configuration.getHost(), configuration.getPort());
        this.db = mongo.getDB(configuration.getDb());
    }

    public void start() throws Exception {

    }

    public void stop() throws Exception {
        this.mongo.close();
    }

    public DB getDb() {
        return db;
    }
}
