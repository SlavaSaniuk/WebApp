package by.bsac.data.dao;

import by.bsac.models.User;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDaoImpl is implementation of {@link by.bsac.data.dao.UserDao} interface.
 * Represent a repository for managing by application container.
 * Contain CRUD method for access and modifying rows in database.
 */
@Repository("user_repository")
public class UserDaoImpl implements UserDao {

    //Persistence bean ("Shared entity manager")
    @PersistenceContext
    private EntityManager em;

    /**
     * Save given {@link by.bsac.models.User} object to database using ORM.
     * @param a_user - {@link by.bsac.models.User} object that need to be saved.
     * @return {@link java.lang.Long} - generate User ID,
     * or '0' - if user object already exist in database (Only caused, if a_user contains a defined user_id field).
     */
    @Override
    public long create(User a_user) {

        try{
            //Associate given object with database
            this.em.persist(a_user);
        }catch (EntityExistsException exc) {

            //Print exception message
            exc.printStackTrace();

            //Return 0;
            return 0;

        }

        //Return generated user ID
        return a_user.getUserId();
    }

    /**
     * Retrieves all users object from database.
     * @return {@link java.util.List<by.bsac.models.User>} contains all users object,
     * or empty list if users not found in database.
     */
    @Override
    public List<User> findAll() {

        //Get all users from database
        List founded_objects = em.createQuery("SELECT users FROM User users").getResultList();

        //Create empty users list
        List<User> founded_users = new ArrayList<>();

        //Cast Objects to User type
        for(Object obj: founded_objects) {
            founded_users.add((User) obj);
        }

        //Return list of founded user
        return founded_users;
    }

    /**
     * Retrieve user object from database by given identifier.
     * @param a_id - user identifier.
     * @return - {@link by.bsac.models.User} - associated with given identifier,
     * or null if user with given identifier not exist.
     */
    @Override
    @Nullable
    public User findById(long a_id) {

        //Return founded user or null
        return this.em.find(User.class, a_id);

    }

    /**
     * Retrieve user object from database by given email address.
     * @param a_email - user email address.
     * @return - {@link by.bsac.models.User} - associated with given email address,
     * or null if user with given email not exist in database.
     */
    @Override
    @Nullable
    public User findByEmail(String a_email) {

        //Create user variable
        User founded_user;

        try {

            //Create query object
            Query find_by_email = em.createQuery("SELECT user FROM User user WHERE user.userEmail = :email");

            //Set named parameters to query
            find_by_email.setParameter("email", a_email);

            //Get founded user object
            founded_user = (User) find_by_email.getSingleResult();
        }catch (NoResultException exc) {

            //Print exc message
            exc.printStackTrace();

            //Return null
            return null;
        }

        //Return founded user object
        return founded_user;
    }

    /**
     * Update user entity in database.
     * @param a_user - user object with defined ID field and updated class fields.
     */
    @Override
    public void update(User a_user) {

        //Find user and retrieve it from database
        User update_user = em.find(User.class, a_user.getUserId());

        //If user does not exist in database, exit from method
        if(update_user == null) return;

        //Update parameters
        update_user.setUserEmail(a_user.getUserEmail());
        update_user.setUserPass(a_user.getUserPass());
    }

    /**
     * Delete user entity from database.
     * @param a_user - user object with defined ID field that need to be deleted.
     */
    @Override
    public void delete(User a_user) {

        //Find user and retrieve it from database
        User remove_user = em.find(User.class, a_user.getUserId());

        //If user does not exist in database, exit from method
        if(remove_user == null) return;

        //remove user from database
        em.remove(remove_user);

    }

    /**
     * Destroy method for closing entity manager.
     */
    @PreDestroy
    public void closeRepository() {

        //Close entity manager
        this.em.close();

    }
}
