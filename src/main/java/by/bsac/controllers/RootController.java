package by.bsac.controllers;

import by.bsac.models.User;
import by.bsac.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller uses for handing request on '/' path.
 * Return /WEB-INF/views/index.html view.
 */
@Controller
@RequestMapping("/")
public class RootController {

    @Autowired
    private AuthenticationService authentication_service;

    /**
     * Method handing HTTP GET request.
     * @return - logical view name.
     */
    @GetMapping
    public String getRootView(Model a_model) {

        a_model.addAttribute("user", new User());
        return "index";
    }


    @PostMapping
    public String authenticateUser(User a_user) {

        this.authentication_service.registerUser(a_user);

        return "index";
    }

}
