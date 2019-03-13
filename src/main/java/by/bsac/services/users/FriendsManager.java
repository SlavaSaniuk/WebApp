package by.bsac.services.users;

import by.bsac.data.dao.FriendsDao;
import by.bsac.models.FriendsRelationship;
import by.bsac.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service(value = "friends_service")
public class FriendsManager {

    //Class global variables
    private FriendsDao friends_dao;

    @Transactional
    public void addToFriends(User master, User slave) {

        //Create friendly relationship
        FriendsRelationship rel = new FriendsRelationship(master, slave);

        //Persist in database
        this.friends_dao.create(rel);

    }


    //Spring beans
    public void setFriendsDao(FriendsDao a_friends_dao) {

        //Mapping
        this.friends_dao = a_friends_dao;

    }

}
