package by.bsac.controllers;

import by.bsac.data.dao.FriendsDao;
import by.bsac.data.dao.UserDao;
import by.bsac.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
@SessionAttributes("common_user")
public class UserController {

    private UserDao user_dao;
    private FriendsDao friends_dao;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{user_id}")
    public String getCommonUserPage(@PathVariable("user_id") long user_id, Model model) {

        User given_user = this.user_dao.findById(user_id);

        model.addAttribute("given_user", given_user);

        return "user";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{user_id}")
    public String handlePostMethod(@PathVariable("user_id") long user_id, HttpServletRequest a_req) {

        //Get common user object from session
        User common_user = (User) a_req.getSession(false).getAttribute("common_user");
        System.out.println(common_user.getUserEmail());

        //Get adding user object from db
        User given_user = this.user_dao.findById(user_id);
        System.out.println(given_user.getUserEmail());

        //Establish friendships
        this.friends_dao.create(common_user, given_user);

        //Return name of user view
        return "user";
    }

    //Spring auto wiring beans
    @Autowired
    public void autowire(UserDao user_dao, FriendsDao friends_dao) {

        this.user_dao = user_dao;
        this.friends_dao = friends_dao;
    }
}
