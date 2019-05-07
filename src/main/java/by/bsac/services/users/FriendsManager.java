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

    //Spring beans
    private FriendsDao friends_dao;

    @Transactional
    public void addToFriends(User master, User slave) {

        //Create friendly relationship
        FriendsRelationship rel = new FriendsRelationship(master, slave);

        //Persist in database
        master.getMyInvitedFriends().add(rel);

        //Persist in database
        this.friends_dao.create(rel);

    }

    /**
     * Validate "add to friends" request. Check situations, if common user wants add myself to friends set
     * or wants to add user, which already exist in friends set. Uses by ajax request, thymeleaf rendering.
     * @param master - common user object.
     * @param slave - given user object.
     * @return - {@link java.lang.Boolean} 'true' if friends request valid.
     */
    public boolean validate(User master, User slave) {

        //Check, if user want to add myself in friends.
        if(master.equals(slave)) return false;

        //Check, if slave user already exist in friends set.
        if (master.wrap().isFriend(slave)) return false;

        //If all OK then return true
        return true;
    }

    //Spring autowiring
    public void setFriendsDao(FriendsDao a_friends_dao) {

        //Mapping
        this.friends_dao = a_friends_dao;

    }

}
