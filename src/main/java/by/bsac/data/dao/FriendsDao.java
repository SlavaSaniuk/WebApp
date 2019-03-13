package by.bsac.data.dao;

import by.bsac.models.FriendsRelationship;
import by.bsac.models.User;

import java.util.List;

/**
 *
 */
public interface FriendsDao {

    /** Interface methods */
    void create(FriendsRelationship rel);

    void removeFriend(User a_user);

    List<User> getFriendsList();
}
