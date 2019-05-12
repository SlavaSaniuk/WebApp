package by.bsac.data.dao;

import by.bsac.models.FriendsRelationship;
import by.bsac.services.users.friends.FriendsStatus;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 *
 */
public interface FriendsDao extends CRUD<FriendsRelationship> {

    //Find section
    /**
     * Find unique friends relationship by ID, and return its.
     * @param relationship_id - FriendsRelationship ID.
     * @return {@link by.bsac.models.FriendsRelationship} unique object.
     */
    @Nullable
    FriendsRelationship findRelationshipById(long relationship_id);

    /**
     * Find and return friends relationship by it members.
     * @param master_id - master ID of relationship.
     * @param slave_id - slave ID of relationship.
     * @return {@link by.bsac.models.FriendsRelationship} unique object.
     */
    @Nullable
    FriendsRelationship findRelationshipByMembers(long master_id, long slave_id);

    /**
     * Find and return unconfirmed submitted user request.
     * Method find friends relationships with status 'UNCONFIRMED' (0) and
     * 'master_id' (friends_one_id) = user_id.
     * @param user_id -  master user ID.
     * @return - {@link java.util.List<by.bsac.models.FriendsRelationship>} of unconfirmed submitted friends request of specific user.
     *              If user don't have a submitted unconfirmed request, then list must be an empty.
     */
    List<FriendsRelationship> getSentUserRequests(long user_id);

    /**
     * Find and return unconfirmed received user request.
     * Method find friends relationships with status 'UNCONFIRMED' (0) and
     * 'slave_id' (friends_two_id) = user_id.
     * @param user_id -  slave user ID.
     * @return - {@link java.util.List<by.bsac.models.FriendsRelationship>} of unconfirmed received friends request of specific user.
     *              If user don't have a received unconfirmed request, then list must be an empty (Method size() = 0).
     *
     */
    List<FriendsRelationship> getReceivedUserRequests(long user_id);


    /**
     *  Find and return all unconfirmed friends requests of specific user (User specifying by specific id to method parameter).
     *  Method search in "friends_relationships" table a FriendsRelationship objects with status = 0 (Unconfirmed request)
     *  and friends_slave_id = specified user ID.
     * @param a_user_id - specified User ID.
     * @return - List of {@link by.bsac.models.FriendsRelationship} unconfirmed (status = 0) objects.
     */
    List<FriendsRelationship> getUnconfirmedFriendsRequests(long a_user_id);



    /**
     * Update status of unique FriendsRelationship object.
     * Find relationship by its ID and change its status.
     * @param relationship_id - ID of friends relationship.
     * @param status - Status of relationship (by default 0).
     */
    void updateRelationshipStatus(long relationship_id, FriendsStatus status);


}
