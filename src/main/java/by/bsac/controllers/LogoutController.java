package by.bsac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/logout")
public class LogoutController {

    @GetMapping
    public String logout(HttpServletRequest a_req) {

        //Get session object
        HttpSession user_session = a_req.getSession(false);

        //Terminate session
        user_session.invalidate();

        //Redirect to "login" page
        return "redirect:/";
    }
}
