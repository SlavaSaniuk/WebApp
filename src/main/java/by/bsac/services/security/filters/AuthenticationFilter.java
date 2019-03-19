package by.bsac.services.security.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/user/*")
@Order(1)
public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        //Cast parameters
        HttpServletRequest a_req = (HttpServletRequest) req;
        HttpServletResponse a_resp = (HttpServletResponse) res;

        //Get session object
        HttpSession user_session = a_req.getSession(true);

        //Check 'authenticated parameter'
        Boolean authenticated = (Boolean) user_session.getAttribute("authenticated");

        if (authenticated == null || !authenticated) {

            //Redirect to login page
            a_resp.sendRedirect("/");

            //Log
            LOGGER.info("Not authorized access to " +a_req.getRequestURL());

            return;
        }

        doFilter(req, res, chain);


    }
}
