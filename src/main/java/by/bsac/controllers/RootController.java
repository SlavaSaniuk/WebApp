package by.bsac.controllers;

import by.bsac.exceptions.AuthenticationException;
import by.bsac.exceptions.AuthenticationMessages;
import by.bsac.models.User;
import by.bsac.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @ModelAttribute("user_form_obj")
    public User getUserFormObj() {
        return new User();
    }

    /**
     * Method handing HTTP GET request.
     * @return - logical view name.
     */
    @GetMapping
    public String getRootView() {
        return "index";
    }


    @PostMapping
    public String authenticateUser(@ModelAttribute("user_form_obj") @Valid User user_obj, BindingResult errors) {

        //Check on validity users input
        if(errors.hasFieldErrors()) {
            //if user
            return "index";
        }


        //Try to register user in system
        try {
            User registered_user = authentication_service.registerUser(user_obj);

        }catch (AuthenticationException exc) {

            FieldError email_err = new FieldError("user_form_obj",
                    "userEmail", user_obj.getUserEmail() + " already registered.");
            errors.addError(email_err);

            System.out.println(exc.getMessage());

            return "index";

        }

        return "index";
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService auth_serv) {
        this.authentication_service = auth_serv;
    }

}
