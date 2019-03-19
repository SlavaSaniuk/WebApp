package by.bsac.services.security;

import by.bsac.data.dao.UserDao;
import by.bsac.exceptions.AuthenticationException;
import by.bsac.exceptions.AuthenticationMessages;
import by.bsac.models.User;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  Authentication service used for authenticate users in system, or create users entities in database.
 *  Work with user DAO implementation.
 */
@Service("authentication_service")
public class AuthenticationService {

    //Global class variables
    //User DAO implementation
    private UserDao user_dao;

    //Constructor
    /**
     * Create new authentication service with specified user DAO implementation.
     * @param a_user_dao - custom user DAO implementation.
     */
    public AuthenticationService(UserDao a_user_dao ) {

        //Mapping users DAO
        this.user_dao = a_user_dao;

    }

    //Methods
    /**
     * Register given user in database.
     * @param a_user - user object, which must be registered.
     * @return - generated user ID.
     * @throws AuthenticationException - if user email address already exist in database.
     */
    @Transactional
    public long registerUser(User a_user) throws AuthenticationException {

        //Check on user already exist in database:
        //By email:
        User checked_user = this.user_dao.findByEmail(a_user.getUserEmail());

        //If exist, throw new exception
        if(checked_user != null)
            throw new AuthenticationException(AuthenticationMessages.EMAIL_ALREADY_REGISTERED);

        //Prepare to persist in database
        // Hashing passwords
        a_user.prepareToPersist();

        //Create user in database,
        //And return generated ID
        return this.user_dao.create(a_user);


    }

    @Nullable
    public User authenticateUser(User a_user) {

        //Search user in database by email
        User founded_user = user_dao.findByEmail(a_user.getUserEmail());

        //If user not found
        //Then throw authentication exception
        if (founded_user == null) return null;

        //Hashing entered password
        //with password salt of founded user
        String pass_hash = User.hashPassword(a_user.getUserPass(), founded_user.getPassSalt());

        //Compare passwords hash's
        //Return user if passwords hash's are equals
        if (pass_hash.equals(founded_user.getUserPass())) return founded_user;
        else return null;

    }

}
