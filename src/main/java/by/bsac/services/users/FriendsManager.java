package by.bsac.services.users;

import by.bsac.models.FriendsRelationship;
import by.bsac.models.User;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service(value = "friends_servcie")
public class FriendsManager {

    public void addToFriends(User master, User slave) {

        //Create friendly relationship
        FriendsRelationship rel = new FriendsRelationship(master, slave);

    }


}
