package by.bsac.controllers;


 import by.bsac.exceptions.AuthenticationException;
 import by.bsac.models.User;
import by.bsac.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
 import org.springframework.validation.FieldError;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @ModelAttribute("userObj")
    public User getUserObj() {
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
    public String authenticateUser(@ModelAttribute("userObj") @Valid User user_obj, BindingResult errors, RedirectAttributes model) {

        //Validate user inputs
        //If user inputs has errors
        //Then return to login view
        if(errors.hasFieldErrors()) return "index";

        //Hold authenticated user
        User authenticated_user;

        //Try to authenticate user in database
        try{

            authenticated_user = this.authentication_service.authenticateUser(user_obj);

            //If user not found in database
        }catch (AuthenticationException exc) {

            //Add field error to errors object
            errors.addError(new FieldError("userObj", "userEmail", exc.getMessage()));

            //Return to login view
            return "index";
        }

        //Check on user authenticated
        if (authenticated_user == null) {

            //Add field error to errors object
            errors.addError(new FieldError("userObj", "userPass", "Password are incorrect"));

            //Return to login view
            return "index";
        }

        //Add user object to session as flash attribute
        model.addFlashAttribute("common_user", authenticated_user);

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
