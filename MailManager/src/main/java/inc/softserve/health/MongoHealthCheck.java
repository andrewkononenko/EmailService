package inc.softserve.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.mongodb.DB;
import inc.softserve.dao.MongoManaged;

public class MongoHealthCheck extends HealthCheck {

    private MongoManaged mongo;

    @Inject
    public MongoHealthCheck(MongoManaged mongo) {
        this.mongo = mongo;
    }

    protected Result check() {
        try{
            DB db = mongo.getDb();
            db.getStats();
        }catch(Exception e){
            return Result.unhealthy("Mongo DB not accessible");
        }
        return Result.healthy();
    }
}
