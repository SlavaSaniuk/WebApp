package by.bsac.services.security.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 */
public class UserAuthInterceptor implements HandlerInterceptor {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //Get session object and check on "authenticated" flag
        HttpSession user_session = request.getSession();
        Boolean authenticated = (Boolean) user_session.getAttribute("authenticated");

        if (authenticated == null || authenticated.equals(Boolean.FALSE)) {

            //Log
            LOGGER.info("Not authorized access to " +request.getRequestURL());

            //Send redirect to login page
            response.sendRedirect("/");

            //Force exit
            return false;
        }

        return true;
    }

}
