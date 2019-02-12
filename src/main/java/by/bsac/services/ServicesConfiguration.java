package by.bsac.services;

import by.bsac.data.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;

/**
 * Configuration class to define and handle application services.
 * Define application services.
 */
@Configuration
public class ServicesConfiguration {

    /**
     * Service used for authenticate / register users in system.
     * Service has two methods: authenticate user in system and register them.
     * @param user_dao - User DAO implementation from DataConfiguration class.
     * @return - Authentication service bean.
     */
    @Bean("authentication_service")
    @Description("Authentication service user for authenticate/register user in system. ")
    @Autowired // UserDAO bean from DataConfiguration class.
    @Scope("request")
    public AuthenticationService getAuthenticationService(UserDao user_dao) {

        //Return statement
        return new AuthenticationService(user_dao);

    }


}
