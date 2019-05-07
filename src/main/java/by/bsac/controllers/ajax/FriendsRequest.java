package by.bsac.controllers.ajax;

import by.bsac.controllers.ajax.jsonJavaObjects.UserID;
import by.bsac.data.dao.UserDao;
import by.bsac.models.User;
import by.bsac.services.users.FriendsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ajax controller which process all friends request, e.g. "add to friends" request.
 */
@Controller
public class FriendsRequest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FriendsRequest.class);

    //Spring beans
    private FriendsManager friends_manager;
    private UserDao user_dao;

    /**
     * Method add given user (founds by his ID) to common user friends set. Called by AJAX
     * with JSON data ("user_id": ID). Find given user in database. Get common user object from HTTP session.
     * Set response header "Content-Type" to "Application/JSON" value. Createa friends relationship in database.
     * @param user_id - given user identification.
     * @param a_req - HttpServletRequest object. (AJAX request).
     * @param a_resp - HttpServletResponse object. (AJAX response).
     */
    @RequestMapping(value = "/addToFriend", method = RequestMethod.POST)
    @ResponseBody
    public void addToFriends(@RequestBody UserID user_id, HttpServletRequest a_req, HttpServletResponse a_resp) {

        //Set response content type
        a_resp.setContentType("application/json");

        //Get common user object from session
        User common_user = (User) a_req.getSession(false).getAttribute("common_user");

        //Find given user object
        User given_user = user_dao.findById(user_id.getUser_id());

        //Validate friends request
        if (friends_manager.validate(common_user, given_user)) {

            //Establish friendships
            this.friends_manager.addToFriends(common_user, given_user);

            //Log
            LOGGER.info("User (id" +common_user.getUserId() +") wants to add user (id" +user_id.getUser_id()  +") in friends set.");
        }

    }





    @RequestMapping(value = "/getFriendsStatus", method = RequestMethod.POST)
    @ResponseBody
    public int getFriendsStatus() {
        return 0;
    }

    //Spring auto wiring
    @Autowired
    public void autowire(FriendsManager friends_manager, UserDao user_dao) {
        this.friends_manager = friends_manager;
        this.user_dao = user_dao;
    }
}
