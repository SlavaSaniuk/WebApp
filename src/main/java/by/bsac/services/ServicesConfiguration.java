package by.bsac.services;

import by.bsac.data.dao.FriendsDao;
import by.bsac.data.dao.UserDao;
import by.bsac.services.security.AuthenticationService;
import by.bsac.services.users.friends.FriendsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Configuration class to define and handle application services.
 * Define application services.
 */
@Configuration
public class ServicesConfiguration {

    //Beans from data configuration class
    @Resource
    private UserDao user_dao;

    @Resource
    private FriendsDao friends_dao;

    @Bean
    public AuthenticationService createAuthenticationService() {
        return new AuthenticationService(user_dao);
    }

    @Bean(name = "friends_manager")
    public FriendsManager getFriendsManager() {

        //Create empty friends manager bean
        FriendsManager fm = new FriendsManager();

        //Set friends DAO implementation
        fm.setFriendsDao(this.friends_dao);

        //Return them
        return fm;
    }


}
