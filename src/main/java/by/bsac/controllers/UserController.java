package by.bsac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Controller
@SessionAttributes("common_user")
public class UserController {

    @RequestMapping(method = RequestMethod.GET, value = "/user/{user_id}")
    public String getCommonUserPage(@PathVariable("user_id") long user_id) {
        return "user";
    }
}
