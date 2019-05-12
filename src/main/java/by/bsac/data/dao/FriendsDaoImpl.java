package by.bsac.data.dao;

import by.bsac.models.FriendsRelationship;
import by.bsac.services.users.friends.FriendsStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

/**
 *
 */
@Repository
public class FriendsDaoImpl implements FriendsDao {

    //Logger
    private static Logger LOGGER = LoggerFactory.getLogger(FriendsDaoImpl.class);

    //Spring beans
    @PersistenceContext
    private EntityManager em;

    /*
        <-- CRUD implementation -->
     */

    /**
     * Persist new "friends relationship" row in database.
     * @param rel - {@link by.bsac.models.FriendsRelationship} new created relationship.
     */
    @Override
    @Transactional
    public void create(FriendsRelationship rel) {

        //Persist relationship into database
        try {
            this.em.persist(rel);
        } catch (EntityExistsException exc) {
            exc.printStackTrace();
        }


    }

    /**
     * Find and return all friends relationships in "friends_relationships" table.
     *
     * @return - {@link java.util.Set<FriendsRelationship>} of all "friends relationships".
     */
    @Override
    public Collection<FriendsRelationship> read() {

        List relationships = em.createQuery("SELECT rel FROM FriendsRelationship rel").getResultList();

        Set<FriendsRelationship> all_relationships = new HashSet<>();

        for (Object rel : relationships) {
            all_relationships.add((FriendsRelationship) rel);
        }

        return all_relationships;
    }

    @Override
    public void delete(FriendsRelationship friendsRelationship) {

    }

    /*
        <-- End of CRUD implementation -->
     */

    /*
        <-- DAO implementation -->
            Find section
     */

    /**
     * Find unique friends relationship by ID, and return its.
     * @param relationship_id - FriendsRelationship ID.
     * @return {@link by.bsac.models.FriendsRelationship} unique object or null if the entity does
     * not exist.
     */
    @Override
    @Nullable
    public FriendsRelationship findRelationshipById(long relationship_id) {
        return this.em.find(FriendsRelationship.class, relationship_id);
    }

    /**
     * Find and return friends relationship by it members.
     * @param master_id - master ID of relationship.
     * @param slave_id - slave ID of relationship.
     * @return {@link by.bsac.models.FriendsRelationship} unique object or null if the entity does
     * not exist or more than one entity.
     */
    @Override
    @Nullable
    public FriendsRelationship findRelationshipByMembers(long master_id, long slave_id) {

        //Create query
        Query q = this.em.createQuery("SELECT rel FROM FriendsRelationship rel WHERE rel.friend_master = :masterID " +
                "AND rel.friend_slave = :slaveID", FriendsRelationship.class);

        //Set parameters to query
        q.setParameter("masterID", master_id);
        q.setParameter("slaveID", slave_id);

        //Execute query to database, and return result
        try {
            return (FriendsRelationship) q.getSingleResult();
        }catch (NoResultException | NonUniqueResultException exc) {
            LOGGER.trace(exc.getMessage());
            return null;
        }
    }

    /**
     * Find and return unconfirmed submitted user request.
     * Method find friends relationships with status 'UNCONFIRMED' (0) and
     * 'master_id' (friends_one_id) = user_id.
     * @param user_id -  master user ID.
     * @return - {@link java.util.List<by.bsac.models.FriendsRelationship>} of unconfirmed submitted friends
     * request of specific user. If user don't have a submitted unconfirmed request, then list must be an empty.
     */
    @Override
    public List<FriendsRelationship> getSentUserRequests(long user_id) {

        //Create resulting list
        List<FriendsRelationship> result = new ArrayList<>();

        //Create query to database
        Query q = this.em.createQuery("SELECT rel FROM FriendsRelationship rel WHERE rel.friend_master = :master_id AND rel.rel_status = 0", FriendsRelationship.class);

        //Set parameter to query
        q.setParameter("master_id", user_id);

        //Execute query
        List founded_entities = q.getResultList();

        //Cast founded entities to FriendsRelationship
        for (Object rel : founded_entities) result.add((FriendsRelationship) rel);

        return result;
    }

    /**
     * Find and return unconfirmed received user request.
     * Method find friends relationships with status 'UNCONFIRMED' (0) and
     * 'slave_id' (friends_two_id) = user_id.
     * @param user_id -  slave user ID.
     * @return - {@link java.util.List<by.bsac.models.FriendsRelationship>} of unconfirmed received friends
     * request of specific user. If user don't have a received unconfirmed request, then list must be an empty
     * (Method size() = 0).
     */
    @Override
    public List<FriendsRelationship> getReceivedUserRequests(long user_id) {

        //Create resulting list
        List<FriendsRelationship> result = new ArrayList<>();

        //Create query to database
        Query q = this.em.createQuery("SELECT rel FROM FriendsRelationship rel WHERE rel.friend_slave = :slave_id AND rel.rel_status = 0", FriendsRelationship.class);

        //Set parameter to query
        q.setParameter("slave_id", user_id);

        //Execute query
        List founded_entities = q.getResultList();

        //Cast founded entities to FriendsRelationship
        for (Object rel : founded_entities) result.add((FriendsRelationship) rel);

        return result;
    }


    /**
     * Find and return all unconfirmed friends requests of specific user (User specifying by specific id to method parameter).
     * Method search in "friends_relationships" table a FriendsRelationship objects with status = 0 (Unconfirmed request)
     * and friends_slave_id = specified user ID.
     *
     * @param a_slave_id - specified User ID.
     * @return - List of {@link by.bsac.models.FriendsRelationship} unconfirmed (status = 0) objects.
     */
    @Override
    public List<FriendsRelationship> getUnconfirmedFriendsRequests(long a_slave_id) {

        //Create JPQL query
        Query q = this.em.createQuery("SELECT rel FROM FriendsRelationship rel WHERE rel.friend_slave.id = :slave_id AND rel.rel_status = 0");

        //Set parameter to query
        q.setParameter("slave_id", a_slave_id);

        //Execute query to database
        List friends_requests = q.getResultList();

        //Log
        LOGGER.info("User (id" + a_slave_id + ") has a " + friends_requests.size() + " unconfirmed friends requests.");

        //Resulting object
        ArrayList<FriendsRelationship> result = new ArrayList<>();

        //Cast to FriendsRelationship objects
        for (Object rel : friends_requests) {
            result.add((FriendsRelationship) rel);
        }

        //Return statement
        return result;
    }

    /**
     * Update status of unique FriendsRelationship object.
     * Find relationship by its ID and change its status.
     * @param relationship_id - ID of friends relationship.
     * @param status          - Status of relationship (by default 0).
     * @throws IllegalArgumentException - if Relationship with given ID don't exist in database.
     */
    @Override
    @Transactional
    public void updateRelationshipStatus(long relationship_id, FriendsStatus status) {

        //Find given relationship by ID
        FriendsRelationship rel = this.findRelationshipById(relationship_id);

        //Check for null
        if (rel == null) {
            LOGGER.warn("Relationship ID: " +relationship_id +" not found in database.");
            throw new IllegalArgumentException("Relationship ID: " +relationship_id +" not found in database.");
        }

        //Update status
        switch (status) {
            case UNCONFIRMED:
                rel.setRelStatus(0);
                break;
            case FRIEND:
                rel.setRelStatus(1);
                break;
            case NOTFRIEND:
                rel.setRelStatus(2);
                break;
            default:
                LOGGER.warn("Unknown relationship status \"" +status +"\".");
                rel.setRelStatus(0);
        }

        //Commit transaction
        this.em.getTransaction().commit();
    }


}