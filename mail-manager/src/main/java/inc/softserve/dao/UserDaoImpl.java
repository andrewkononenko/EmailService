package inc.softserve.dao;

import com.google.inject.Inject;
import inc.softserve.User;
import org.mongojack.DBCursor;
import org.mongojack.DBProjection;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

public class UserDaoImpl implements UserDao {

    private MongoManaged mongo;
    private JacksonDBCollection<User, String> users;

    @Inject
    public UserDaoImpl(MongoManaged mongo) {
        this.mongo = mongo;
        users = JacksonDBCollection.wrap(mongo.getDb().getCollection("users"), User.class, String.class);
    }

    public User getUserByUsername(String username) {
        DBCursor<User> cursor = users.find(DBQuery.is("username", username),DBProjection.exclude("_id"));
        if(cursor.hasNext()) {
            return cursor.next();
        }
        return null;
    }
}
