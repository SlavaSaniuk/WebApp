package by.bsac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 */
@Controller
public class UserController {

    @RequestMapping(method = RequestMethod.GET, value = "/user/{user_id}")
    public String getCommonUserPage(@PathVariable("user_id") long user_id) {
        return "user";
    }
}
