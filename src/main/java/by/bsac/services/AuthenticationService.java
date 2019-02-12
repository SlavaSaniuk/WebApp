package by.bsac.services;

import by.bsac.data.dao.UserDao;
import by.bsac.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Authentication service used for authenticate users in system, or create users in system.
 *  Work with
 */
@Service("authentication_service")
public class AuthenticationService {

    private UserDao user_dao;

    @Autowired
    public AuthenticationService(UserDao a_user_dao ) {

        //Mapping users DAO
        this.user_dao = a_user_dao;

    }

    public void registerUser(User a_user) {

        this.user_dao.create(a_user);

    }

}
