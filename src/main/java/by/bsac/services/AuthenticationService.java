package by.bsac.services;

import by.bsac.data.dao.UserDao;
import by.bsac.exceptions.WebAppException;
import by.bsac.models.User;
import org.springframework.stereotype.Service;

/**
 *  Authentication service used for authenticate users in system, or create users rows in database.
 *  Work with user DAO implementation.
 */
@Service("authentication_service")
public class AuthenticationService {

    // user DAO implementation.
    private UserDao user_dao;

    /**
     * Create new authentication service with specified user DAO implementation.
     * @param a_user_dao - custom user DAO implementation.
     */
    public AuthenticationService(UserDao a_user_dao ) {

        //Mapping users DAO
        this.user_dao = a_user_dao;

    }

    /**
     * Register specified user object in system. Create new user row in database.
     * @param a_user - custom user object. Must contain a user email a user password values.
     *                  User email value must be unique.
     */
    public User registerUser(User a_user) throws WebAppException{

        //Check for existence user with same email address
        //If exist - cause new exception.
        if(this.user_dao.findByEmail(a_user.getUserEmail()) != null)
                throw new WebAppException("User with email: " +a_user.getUserEmail() +" already exist in database.");

        //Create users row in database and get generated ID:
        long generated_id  = user_dao.create(a_user);

        //Return created user object:
        return this.user_dao.findById(generated_id);

    }

}
