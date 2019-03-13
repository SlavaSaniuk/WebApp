package by.bsac.models;

import java.util.HashSet;
import java.util.Set;

public class UserWrapper {

    private User common_user;

    //Constructor
    public UserWrapper(User a_user) {

        //Mapping
        this.common_user = a_user;

    }

    //Methods
    //Getters and setters
    public User getUser() {
        return common_user;
    }

    //User detail section
    /**
     * Get user full name (first + last name)
     * @return - {@link java.lang.String} - user full name.
     */
    public String getFullName() {
        return this.common_user.getUserDetail().getfName() + " " + this.common_user.getUserDetail().getlName();
    }

    //Friends relationships section
    /**
     * All users friends.
     * @return - {@link java.util.Set<User>} - Set of all user friends as {@link by.bsac.models.User} objects.
     */

    public Set<User> getFriendsSet() {

        //Create empty result set
        Set<User> friends = new HashSet<>();

        //Add invited user friends to resulting set
        friends.addAll(this.common_user.getInvitedMeFriends());

        //Add invited friends by user to resulting set
        friends.addAll(this.common_user.getMyInvitedFriends());

        //Return resulting set
        return friends;
    }

}
