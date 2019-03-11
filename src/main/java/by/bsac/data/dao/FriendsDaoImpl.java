package by.bsac.data.dao;

import by.bsac.models.FriendsRelationship;
import by.bsac.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
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
    @Transactional
    public void create(User common_user, User a_friend) {

        //Create new friends relationship
        FriendsRelationship rel = new FriendsRelationship(common_user, a_friend);

        //Persist it into database
        try {
            this.em.persist(rel);
        }catch (EntityExistsException exc) {
            exc.printStackTrace();
        }


    }

    @Override
    public void removeFriend(User a_user) {

    }

    @Override
    public List<User> getFriendsList() {
        return null;
    }

}
