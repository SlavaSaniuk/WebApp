package by.bsac.main;

import by.bsac.services.security.interceptors.UserAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;


import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.util.Locale;


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

    //Servlet context
    private ServletContext servlet_context;

    //Beans definition
    //Thymeleaf beans
    /**
     * Create a Thymeleaf template resolver engine. Resolve template names.
     * @return - SpringResourceTemplateResolver - common template resolver.
     */
    @Bean
    public ServletContextTemplateResolver contextTemplateResolver() {
        ServletContextTemplateResolver tr = new ServletContextTemplateResolver(servlet_context);
        tr.setPrefix("/WEB-INF/views/");
        tr.setSuffix("./html");
        tr.setTemplateMode(TemplateMode.HTML);
        tr.setCacheable(false);
        tr.setOrder(1);
        return tr;
    }

    @Bean
    @Description("Thymeleaf template resolver.")
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.application_context);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setOrder(2);
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
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver view_resolver = new ThymeleafViewResolver();
        view_resolver.setTemplateEngine(this.templateEngine());
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

        registry.addResourceHandler("/img/**", "/css/**", "/libs/**","/lang/**").addResourceLocations(
                "classpath:/static/img/",
                "classpath:/static/css/",
                "classpath:/static/libs/",
                "classpath:/static/lang/"
        );
    }

    //Spring interceptors
    //User authentication interceptor
    @Bean
    public UserAuthInterceptor createUserAuthInterceptor() {
        return new UserAuthInterceptor();
    }

    @Bean
    public LocaleChangeInterceptor createLocaleChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createUserAuthInterceptor()).addPathPatterns("/user/{user_id}");
        registry.addInterceptor(createLocaleChangeInterceptor());
    }

    //Spring components
    @Bean
    public LocaleResolver createLocaleResolver() {
        AcceptHeaderLocaleResolver slr = new AcceptHeaderLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {

        ResourceBundleMessageSource messages_source = new ResourceBundleMessageSource();
        messages_source.setBasenames("static/lang/messages");
        messages_source.setDefaultEncoding("UTF-8");
        return messages_source;

    }

    /**
     * Spring beans auto wiring.
     * @param a_application_context - Root application context of WebApp.
     * @param a_servlet_context - Common servlet context.
     */
    @Autowired
    public void springAutoWiring(ApplicationContext a_application_context, ServletContext a_servlet_context) {

        //Mapping
        this.application_context = a_application_context;
        this.servlet_context = a_servlet_context;

    }





}
