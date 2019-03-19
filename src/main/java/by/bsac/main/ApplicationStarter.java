package by.bsac.main;

import by.bsac.services.security.filters.AuthenticationFilter;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


import javax.servlet.*;


import java.io.File;
import java.util.EnumSet;


/**
 *  Main class of Web App.
 *  Create root application context and child web application context. Initialize them.
 *  Create and set 'Dispatcher servlet'.
 */
public class ApplicationStarter implements WebApplicationInitializer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStarter.class);

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

        //Initialize SLF4J Logger
        initializeLogger();

        //Log
        LOGGER.info("Application started");

        //Create Root Application Context
        AnnotationConfigWebApplicationContext root_ctx = new AnnotationConfigWebApplicationContext();

        //Add initialization class
        root_ctx.register(by.bsac.main.RootAppContext.class);

        //Set active profiles
        root_ctx.getEnvironment().setActiveProfiles(SpringProfiles.PRODUCTION.toString());
        root_ctx.getEnvironment().addActiveProfile(SpringProfiles.JPA.toString());

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

        //Disable JSESSIONID in URL
        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));

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

    /**
     * Initialize SLF4J (Log4j) Logger.
     * Load Log4j.properties file from /resources/configuration/ folder.
     */
    private void initializeLogger() {

        //Set for application to use SLF4J instead jboss logging
        System.setProperty("org.jboss.logging.provider", "slf4j");

        //Get log4j properties
        ClassLoader cl = getClass().getClassLoader();

        File props = new File(cl.getResource("/configuration/log4j.properties").getFile());

        PropertyConfigurator.configure(props.getPath());

    }
}
