package by.bsac.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Common Web application context configuration class.
 * Web ppp context is child of Root app context.
 * Defines callback methods to customize the Java-based configuration for
 * Spring MVC enabled via {@code @EnableWebMvc}
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "by.bsac.controllers")
public class WebAppContext implements WebMvcConfigurer {

    //Root application context
    private ApplicationContext application_context;

    //Beans definition
    //Thymeleaf beans:

    /**
     * Create a Thymeleaf template resolver engine. Resolve template names.
     * @return - SpringResourceTemplateResolver - common template resolver.
     */
    @Bean
    @Description("Thymeleaf template resolver.")
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.application_context);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        return  templateResolver;
    }

    /**
     * Create a Thymeleaf template resolver by using template engine.
     * @return - Spring template engine.
     */
    @Bean
    @Description("Thymeleaf template engine.")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    /**
     * Main view resolver. WebApp uses Thymeleaf Java template engine.
     * @return - Thymeleaf view resolver.
     */
    @Bean
    @Description("Main view resolver")
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver view_resolver = new ThymeleafViewResolver();
        view_resolver.setTemplateEngine(this.templateEngine());
        view_resolver.setOrder(10);
        return view_resolver;
    }

    //Overriding WebMvcConfigurer methods

    /**
     * Overriding the Servlet container's default handling of static resources
     * @param configurer - Configures a request handler for serving static resources
     *                  by forwarding the request to the Servlet container's "default" Servlet.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Create static resources handlers.
     * @param registry - path registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/img/**", "/css/**", "/libs/**").addResourceLocations(
                "classpath:/static/img/",
                "classpath:/static/css/",
                "classpath:/static/libs/"
        );
    }

    /**
     * Autowiring beans in Spring.
     * @param a_application_context - Root application context of WebApp.
     */
    @Autowired
    public void setApplicationContext(ApplicationContext a_application_context) {

        //Mapping context:
        this.application_context = a_application_context;

    }



}
