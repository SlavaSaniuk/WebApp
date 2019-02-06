package by.bsac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller uses for handing request on '/' path.
 * Return /WEB-INF/views/index.html view.
 */
@Controller
@RequestMapping("/")
public class RootController {

    /**
     * Method handing HTTP GET request.
     * @return - logical view name.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getRootView() {
        return "index";
    }

}
