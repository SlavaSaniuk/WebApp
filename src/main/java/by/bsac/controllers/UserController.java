package by.bsac.controllers;

import by.bsac.data.dao.UserDao;
import by.bsac.models.User;
import by.bsac.models.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Controller
@SessionAttributes("common_user")
public class UserController {

    private UserDao user_dao;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{user_id}")
    public String getCommonUserPage(@PathVariable("user_id") long user_id, Model model) {

        UserDetail given_user_inf = this.user_dao.findById(user_id).getUserDetail();

        model.addAttribute("user_inf", given_user_inf);

        return "user";
    }

    //Spring auto wiring beans
    @Autowired
    public void setUserDao(UserDao user_dao) {
        this.user_dao = user_dao;
    }
}
