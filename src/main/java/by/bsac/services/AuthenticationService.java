package by.bsac.services;

import by.bsac.data.dao.UserDao;
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
    public void registerUser(User a_user) {

        //Create user row in database.
        this.user_dao.create(a_user);

    }

}
