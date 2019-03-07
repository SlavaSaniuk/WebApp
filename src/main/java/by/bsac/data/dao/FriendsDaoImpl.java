package by.bsac.data.dao;

import by.bsac.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 */
@Repository
public class FriendsDaoImpl implements FriendsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addFriend(User common_user, User a_friend) {

        common_user.getFriends().add(a_friend);
        this.em.persist(common_user);

    }

    @Override
    public void removeFriend(User a_user) {

    }

    @Override
    public List<User> getFriendsList() {
        return null;
    }

}
