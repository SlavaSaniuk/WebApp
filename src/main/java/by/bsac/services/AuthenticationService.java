package by.bsac.services;

import by.bsac.data.dao.UserDao;
import by.bsac.exceptions.AuthenticationException;
import by.bsac.exceptions.AuthenticationMessages;
import by.bsac.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  Authentication service used for authenticate users in system, or create users entities in database.
 *  Work with user DAO implementation.
 */
@Service("authentication_service")
public class AuthenticationService {

    //User DAO implementation.
    private UserDao user_dao;

    /**
     * Create new authentication service with specified user DAO implementation.
     * @param a_user_dao - custom user DAO implementation.
     */
    public AuthenticationService(UserDao a_user_dao ) {

        //Mapping users DAO
        this.user_dao = a_user_dao;

    }

    @Transactional
    public User registerUser(User a_user) throws AuthenticationException {

        //Check on user already exist in database:
        //By email:
        User checked_user = this.user_dao.findByEmail(a_user.getUserEmail());

        //If exist, throw new exception
        if(checked_user != null)
            throw new AuthenticationException(AuthenticationMessages.EMAIL_ALREADY_REGISTERED);

        //hashing password
        a_user.hashPassword();

        //Create user in database,
        //and get generated ID
        long generated_id = this.user_dao.create(a_user);

        //Return new created user
        return this.user_dao.findById(generated_id);
    }

}
