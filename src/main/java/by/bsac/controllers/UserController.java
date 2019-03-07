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

    @PostMapping
    public String handlePostMethod(@ModelAttribute("given_user") User given_user, HttpServletRequest a_req) {

        //Get common user object from session
        User common_user = (User) a_req.getSession(false).getAttribute("common_user");

        friends_dao.addFriend(common_user, given_user);

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
