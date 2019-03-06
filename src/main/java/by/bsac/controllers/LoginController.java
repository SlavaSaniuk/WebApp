package by.bsac.controllers;


import by.bsac.models.User;
import by.bsac.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller uses for handing request on '/' path.
 * Return /WEB-INF/views/index.html view.
 */
@Controller
@RequestMapping("/")
@Scope("request")
public class LoginController {

    //Authentication service bean
    private AuthenticationService authentication_service;

    @ModelAttribute("userObj")
    public User getUserObj() {
        return new User();
    }

    /**
     * Method handing HTTP GET request.
     * @return - login view name (index.html).
     */
    @GetMapping
    public String getRootView() {
        return "index";
    }

    /**
     * Authenticate user in system. Validate user inputs, search user object in database, compare passwords,
     * and set user object to user session.
     * @param user_obj - Common user object (With specified email and clear password).
     * @param errors - Object hold error on user inputs.
     * @param a_req - Session attributes.
     * @return - user view name (/user/user_id.html), or "index.html", if authenticate process is failed
     * or user inputs has errors.
     */
    @PostMapping
    public String authenticateUser(@ModelAttribute("userObj") @Valid User user_obj,
                                   BindingResult errors, HttpServletRequest a_req) {

        //Validate user inputs
        //If user inputs has errors
        //Then return to login view
        if(errors.hasFieldErrors()) return "index";

        //Try to authenticate user in database
        User authenticated_user = this.authentication_service.authenticateUser(user_obj);

        //If user is not authenticated
        //Then return to login view
        if (authenticated_user == null) {

            //Add field error to errors object
            errors.addError(new FieldError("userObj", "userPass", "Email or password is incorrect"));

            //Return to login view
            return "index";
        }

        //Add user object to session as attribute
        a_req.getSession(true).setAttribute("common_user", authenticated_user);

        //If all 'OK',
        //Then redirect to user page
        return "redirect:/user/" +authenticated_user.getUserId();

    }

    //Spring beans auto wiring
    @Autowired
    public void setAuthenticationService(AuthenticationService auth_service) {
        this.authentication_service = auth_service;
    }

}
