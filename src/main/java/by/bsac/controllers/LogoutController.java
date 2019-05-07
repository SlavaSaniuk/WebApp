package by.bsac.controllers;

import by.bsac.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/logout")
public class LogoutController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutController.class);

    @GetMapping
    public String logout(HttpServletRequest a_req) {

        //Get session object
        HttpSession user_session = a_req.getSession(false);
        User common_user = (User) user_session.getAttribute("common_user");

        //Terminate session
        user_session.invalidate();

        //log
        LOGGER.info("User (id" +common_user.getUserId() +") logged out");

        //Redirect to "login" page
        return "redirect:/";
    }
}
