package by.bsac.controllers;

import by.bsac.data.dao.FriendsDao;
import by.bsac.data.dao.UserDao;
import by.bsac.models.User;
import by.bsac.models.UserWrapper;
import by.bsac.services.users.friends.FriendsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 *
 */
@Controller
public class UserController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    //Spring beans
    private UserDao user_dao;
    private FriendsManager friends_manager;
    private FriendsDao friends_dao;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{user_id}")
    public String getUserPage(@PathVariable("user_id") long user_id, Model model, HttpServletRequest a_request) {

        //Get user object from database
        User given_user = this.user_dao.findById(user_id);

        //Create user wrapper
        UserWrapper wrapper = new UserWrapper(given_user);

        //Get common user object from session
        User common_user = (User) a_request.getSession(false).getAttribute("common_user");

        //Add to model
        model.addAttribute("given_user", wrapper);

        //Friends section
        //Get status of friends relationship
        //Set 'addToFriends' flag
        model.addAttribute("addToFriends", this.friends_manager.validate(common_user, given_user));

        //Get amount of unconfirmed friends requests
        int unconfirmed_friends_request = this.friends_dao.getUnconfirmedFriendsRequests(common_user.getUserId()).size();
        //Add it to user session attribute
        a_request.getSession(false).setAttribute("unconfirmed_requests", unconfirmed_friends_request);

        //Return user view
        return "user";
    }

    //Spring autowiring
    @Autowired
    public void autowire(UserDao user_dao, FriendsManager friends_manager, FriendsDao friends_dao) {

        //Autowiring
        this.user_dao = user_dao;
        this.friends_manager = friends_manager;
        this.friends_dao = friends_dao;

    }
}
