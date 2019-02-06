package by.bsac.main;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 *  Main class of Web App.
 *  Create root application context and child web application context. Initialize them.
 *  Create and set 'Dispatcher servlet'.
 */
public class ApplicationStarter implements WebApplicationInitializer {

    /**
     * Configure the given {@link ServletContext} with any servlets, filters, listeners
     * context-params and attributes necessary for initializing this web application. See
     * examples {@linkplain WebApplicationInitializer above}.
     * @param servletContext the {@code ServletContext} to initialize
     * @throws ServletException if any call against the given {@code ServletContext}
     * throws a {@code ServletException}
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //Create Root Application Context
        AnnotationConfigWebApplicationContext root_ctx = new AnnotationConfigWebApplicationContext();

        //Add initialization class
        root_ctx.register(by.bsac.main.RootAppContext.class);

        //Set active profiles
        // ...

        //Refresh context
        root_ctx.refresh();

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

        //Refresh context
        web_ctx.refresh();
    }

}
