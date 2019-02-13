package by.bsac.data.dao;

import by.bsac.models.User;

import java.util.List;

/**
 *  Common used DAO interface.
 *  Implementing class must implements all CRUD methods defined in this interface.
 */
public interface UserDao {

    //Create
    /**
     * Method used to save user information in database.
     * @param a_user - user object with specified email and password fields.
     * @return - generated ID value.
     */
    long create(User a_user);

    //Read

    /**
     * Find all users in database.
     * @return - List of all user contained in database.
     */
    List<User> findAll();

    /**
     * Find user by his identifier.
     * @param a_id - user identifier.
     * @return - user object with specified identifier.
     */
    User findById(long a_id);

    /**
     * Find user by his email address.
     * @param a_email - user email address.
     * @return - user object with specified email address.
     */
    User findByEmail(String a_email);

    //Update
    /**
     * Method used to update user information in database.
     * @param a_user - user object with updated information.
     */
    void update(User a_user);

    //Delete
    /**
     * Method used to delete user information from database.
     * @param a_user - user object with specified email and ID fields.
     */
    void delete(User a_user);

}
