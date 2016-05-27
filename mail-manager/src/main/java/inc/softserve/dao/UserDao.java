package inc.softserve.dao;

import inc.softserve.User;

public interface UserDao {
    User getUserByUsername(String username);
}
