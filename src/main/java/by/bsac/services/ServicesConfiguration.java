package by.bsac.services;

import by.bsac.data.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Configuration class to define and handle application services.
 * Define application services.
 */
@Configuration
public class ServicesConfiguration {

    //Bean from data configuration class
    @Resource
    private UserDao user_dao;

    @Bean
    public AuthenticationService createAuthenticationService() {
        return new AuthenticationService(user_dao);
    }


}
