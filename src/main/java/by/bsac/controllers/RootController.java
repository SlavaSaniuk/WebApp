package by.bsac.controllers;

import by.bsac.exceptions.AuthenticationException;
import by.bsac.models.User;
import by.bsac.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Controller uses for handing request on '/' path.
 * Return /WEB-INF/views/index.html view.
 */
@Controller
@RequestMapping("/")
@Scope("request")
public class RootController {

    private AuthenticationService authentication_service;

    /**
     * Method handing HTTP GET request.
     * @return - logical view name.
     */
    @GetMapping
    public String getRootView(ModelMap model) {
        User usr = new User();
        model.put("usr", usr);
        return "index";
    }


    @PostMapping
    public String authenticateUser(@Valid User user, BindingResult result) {

        //Validate user input
        if (result.hasErrors()) {
            System.out.println("ReSult has error");
            return "index";
        }else System.out.println("RESULT HAS NOT ERRORS");

        System.out.println(user.getUserEmail());
        System.out.println(user.getUserPass());

        //Try to register user in system
        try {
            User registered_user = authentication_service.registerUser(user);

        }catch (AuthenticationException exc) {
            System.out.println(exc.getMessage());
        }

        return "index";
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService auth_serv) {
        this.authentication_service = auth_serv;
    }

}
