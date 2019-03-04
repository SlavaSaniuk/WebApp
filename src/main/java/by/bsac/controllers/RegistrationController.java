package by.bsac.controllers;

import by.bsac.exceptions.AuthenticationException;
import by.bsac.models.User;
import by.bsac.models.UserDetail;
import by.bsac.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 *  Controller handle http request on "/register" URL.
 *  GET request return registration page.
 *  POST request register create new user object with values of entered inputs, try to register them in database,
 *  and return generated user page.
 */
@Controller
@RequestMapping("/register")
@Scope("request")
public class RegistrationController {


    //Used to register users in database.
    private AuthenticationService register;

    //Empty user object
    @ModelAttribute("userObj")
    public User userObj() {
        return new User();
    }

    //Empty UserDetail object
    @ModelAttribute("userDetailObj")
    public UserDetail userDetailObj() {
        return new UserDetail();
    }

    /**
     * Method handle GET request to "/register" URL.
     * Set user and user detail objects to madel attribute.
     * @return - {@link java.lang.String} - name of requested page.
     */
    @GetMapping
    public String getRegistrationForm() {

        //Return "register.html"
        return "register";

    }

    /**
     * Method registered user in database. Validate user input, set user details to given user,
     * check if user email already registered in database. Register user and get generated user ID.
     * Save given user object to session.
     * @param user - Given user object (have user email, and user clear password).
     * @param user_detail - Given user detail object.
     * @param result - object hold fields error.
     * @param model - {@link javax.servlet.http.HttpSession} - session attributes.
     * @return - name of user view, or registration view if error occurs.
     */
    @PostMapping
    public String handingRegistrationRequest(@ModelAttribute("userObj") @Valid User user,
                                             @ModelAttribute("userDetailObj") @Valid UserDetail user_detail,
                                             BindingResult result, RedirectAttributes model) {

        //Validate user input
        //If user inputs has errors, return back
        if (result.hasFieldErrors()) return "register";

        //Set given user detail object to given user
        user.setUserDetail(user_detail);

        //Variable hold user id;
        long generated_id;

        //Try to register user in database
        try {

            //Initialize generated ID
            generated_id = this.register.registerUser(user);

            //If email already registered in database
        } catch (AuthenticationException exc) {

            //Add error to user input
            //And return back
            result.addError(new FieldError("userObj", "userEmail",  exc.getMessage()));
            return "register";
        }

        //Set generated ID to given user
        user.setUserId(generated_id);

        //Add user object to session as flash attribute
        model.addFlashAttribute("common_user", user);

        //If all 'OK',
        //Then redirect to user page
        return "redirect:/user/" +user.getUserId();
    }

    //Spring bean auto wiring
    @Autowired
    public void setRegister(AuthenticationService auth_service) {
        this.register = auth_service;
    }


}
