package by.bsac.main;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 *
 */
public class ApplicationStarter implements WebApplicationInitializer {


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //Create Root Application Context
        AnnotationConfigWebApplicationContext root_ctx = new AnnotationConfigWebApplicationContext();

        //Add initialization class
        root_ctx.register(by.bsac.main.RootAppContext.class);

        //Set active profiles
        // ...

        //Create Web Application Context
        AnnotationConfigWebApplicationContext web_ctx = new AnnotationConfigWebApplicationContext();

        //Add initialization class
        web_ctx.register(by.bsac.main.WebAppContext.class);

        //Add parent context
        web_ctx.setParent(root_ctx);

        //If ServletContext = null, throw exception
        if(servletContext == null) throw new ServletException();

        //Set servlet context
        web_ctx.setServletContext(servletContext);

            //Dispatcher Servlet
            {
                //Create DispatcherServlet
                ServletRegistration.Dynamic dispatcher_servlet = servletContext.addServlet("dispatcher_servlet", new DispatcherServlet(web_ctx));

                //Add parameters to servlet
                dispatcher_servlet.setLoadOnStartup(1);
                dispatcher_servlet.addMapping("/");
            }

    }

}
