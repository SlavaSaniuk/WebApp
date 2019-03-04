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
 *
 */
@Controller
@RequestMapping("/register")
@Scope("request")
public class RegistrationController {

    /**
     * {@link by.bsac.services.AuthenticationService} register - used to register users in database.
     */
    private AuthenticationService register;

    @ModelAttribute("userObj")
    public User userObj() {
        return new User();
    }

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


    @PostMapping
    public String handingRegistrationRequest(@ModelAttribute("userObj") @Valid User user,
                                             @ModelAttribute("userDetailObj") @Valid UserDetail user_detail,
                                             BindingResult result, RedirectAttributes model) {

        //Validate user input
        //If user inputs has errors, return back
        if (result.hasFieldErrors()) return "register";

        //Set given user detail object to given user
        user.setUserDetail(user_detail);

        //Create new user object
        User registered_user;
        //Try to register user in database
        try {

            registered_user = this.register.registerUser(user);

            //If email already registered in database
        } catch (AuthenticationException exc) {

            //Add error to user input
            //And return back
            result.addError(new FieldError("userObj", "userEmail",  exc.getMessage()));
            return "register";
        }

        //Add user object to session as flash attribute
        model.addFlashAttribute("common_user", registered_user);

        //If all 'OK'
        //Return to user page
        return "redirect:/user/" +registered_user.getUserId();
    }

    @Autowired
    public void setRegistrer(AuthenticationService auth_srvc) {
        this.register = auth_srvc;
    }


}
