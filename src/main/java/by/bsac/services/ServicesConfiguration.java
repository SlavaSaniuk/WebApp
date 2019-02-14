package by.bsac.services;

import by.bsac.data.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import javax.annotation.Resource;

/**
 * Configuration class to define and handle application services.
 * Define application services.
 */
@Configuration
public class ServicesConfiguration {

    // UserDAO bean from DataConfiguration class.
    @Resource(name = "user_dao")
    private UserDao user_dao;

    /**
     * Service used for authenticate / register users in system.
     * Service has two methods: authenticate user in system and register them.
     * @return - Authentication service bean.
     */
    @Bean("authentication_service")
    @Description("Authentication service user for authenticate/register user in system. ")
    public AuthenticationService getAuthenticationService() {

        //Return statement
        return new AuthenticationService(user_dao);

    }


}
