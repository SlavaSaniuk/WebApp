package by.bsac.services;

import by.bsac.data.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * Configuration class to define and handle application services.
 * Define application services.
 */
@Configuration
public class ServicesConfiguration {

    @Bean("authentication_service")
    @Description("Authentication service user for authenticate/register user in system. ")
    @Autowired
    public AuthenticationService getAuthenticationService(UserDao user_dao) {

        return new AuthenticationService(user_dao);
    }


}
