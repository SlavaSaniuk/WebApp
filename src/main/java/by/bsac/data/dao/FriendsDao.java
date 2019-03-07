package by.bsac.data.dao;

import by.bsac.models.User;

import java.util.List;

/**
 *
 */
public interface FriendsDao {

    /** Interface methods */
    void addFriend(User common_user, User a_friend);

    void removeFriend(User a_user);

    List<User> getFriendsList();
}
