package by.bsac.controllers;

import by.bsac.data.dao.UserDao;
import by.bsac.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
@RequestMapping(value = "/friends")
public class FriendsController {

    private UserDao user_dao;

    @GetMapping
    public String getFriendsPage(Model a_model, HttpServletRequest a_req) {

        //Get common user wrapper object from session
        User common_user_wrapper = (User) a_req.getSession(false).getAttribute("common_user");

        Set<User> friends_list = common_user_wrapper.getMyInvitedFriends();

        a_model.addAttribute("friends_list", friends_list);

        return "friends";
    }

    //Spring beans auto wiring
    @Autowired
    public void setUserDao(UserDao a_user_dao) {
        this.user_dao = a_user_dao;
    }
}
