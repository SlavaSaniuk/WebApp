package by.bsac.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public User unwrap() {
        return this.common_user;
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
    private Set<User> getMyInvitedFriends() {

        //Create empty set
        Set<User> friends = new HashSet<>();

        //Get friends from relationships
        for (FriendsRelationship rel : common_user.getMyInvitedFriends()) friends.add(rel.getFriendshipSlave());

        //Return set
        return friends;
    }

    private Set<User> getInvitedMeFriends() {

        //Create empty set
        Set<User> friends = new HashSet<>();

        //Get friends from relationships
        for (FriendsRelationship rel : common_user.getInvitedMeFriends()) friends.add(rel.getFriendshipMaster());

        //Return set
        return friends;
    }
    /**
     * All users friends.
     * @return - {@link java.util.Set<User>} - Set of all user friends as {@link by.bsac.models.User} objects.
     */

    public Set<User> getFriendsSet() {

        //Create empty result set
        Set<User> friends = new HashSet<>();

        //Add invited user friends to resulting set
        friends.addAll(getInvitedMeFriends());

        //Add invited friends by user to resulting set
        friends.addAll(getMyInvitedFriends());

        //Return resulting set
        return friends;
    }

    public boolean isFriend(User a_user) {
        return getFriendsSet().contains(a_user);
    }

}
