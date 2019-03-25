package by.bsac.controllers;

import by.bsac.data.dao.UserDao;
import by.bsac.models.User;
import by.bsac.models.UserWrapper;
import by.bsac.services.users.FriendsManager;
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

    private UserDao user_dao;
    private FriendsManager friends_manager;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{user_id}")
    public String getCommonUserPage(@PathVariable("user_id") long user_id, Model model, HttpServletRequest a_request) {

        //Get user object from database
        User given_user = this.user_dao.findById(user_id);

        //Create user wrapper
        UserWrapper wrapper = new UserWrapper(given_user);

        //Get common user object from session
        User common_user = (User) a_request.getSession(false).getAttribute("common_user");

        //Add to model
        model.addAttribute("given_user", wrapper);

        //Add 'isFriend' flag
        //Get wrapper of common user object, and check if given user exist in friends set
        //'isFriend' - Boolean value
        if (!common_user.equals(given_user)) {
            model.addAttribute("isFriend", common_user.wrap().isFriend(given_user));
        }


        //Return user page
        return "user";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{user_id}")
    public String handlePostMethod(@PathVariable("user_id") long user_id, Model model, HttpServletRequest a_req) {

        //Get common user object from session
        User common_user = (User) a_req.getSession(false).getAttribute("common_user");

        //Get adding user object from db
        User given_user = this.user_dao.findById(user_id);

        //Establish friendships
        this.friends_manager.addToFriends(common_user, given_user);

        //Return name of user view
        return "redirect:/user/" +user_id;
    }

    //Spring auto wiring beans
    @Autowired
    public void autowire(UserDao user_dao, FriendsManager friends_manager) {
        this.user_dao = user_dao;
        this.friends_manager = friends_manager;
    }
}
