package by.bsac.data;

import by.bsac.data.dao.UserDao;
import by.bsac.data.dao.UserDaoImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Configuration class for data manipulating.
 * Define JDBC resources (Data sources), Hibernate local session factory.
 * DAO Beans.
 */
@Configuration
@EnableTransactionManagement
//Import database.properties definition class
@PropertySource("classpath:configuration/database.properties")
public class DataConfiguration implements ApplicationContextAware {

    //Application context reference
    private ApplicationContext application_context;

    //Created data source with active profile:
    private DataSource active_datasource;

    //Get values from 'database.property' file
    @Value("${db.url}")
    private String db_url;

    @Value("${db.driver}")
    private String db_driver_path;

    @Value("${db.username}")
    private String db_username;

    @Value("${db.userpass}")
    private String db_userpass;




    /**
     * Get reference to root application context:
     * @param applicationContext - Root application context.
     * @throws BeansException - unchecked runtime exception.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application_context = applicationContext;
    }

    /*
     *   Data sources beans definition.
     *   Called by @Resource annotation.
     *   Data sources for different profiles: "development", "production", "testing".
     */

    /**
     * Create development data source via DriverManagerDataSource class.
     * @return - DataSource object for development purposes.
     */
    @Bean("data_source")
    @Description("DataSource for development profile.")
    @Profile("development")
    public DriverManagerDataSource getDevDataSource() {

        //Create DriverManagerDataSource object:
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Initialize data source:
        ds.setUrl(this.db_url);
        ds.setDriverClassName(this.db_driver_path);
        ds.setUsername(this.db_username);
        ds.setPassword(this.db_userpass);

        //Mapping datasource:
        this.active_datasource = ds;

        //Return configured data source:
        return ds;

    }

    /**
     * Create production data source via JNDI resource.
     * This data source support connection pooling.
     * @return - Datasource object for development purposes.
     */
    @Bean("data_source")
    @Description("DataSource for production profile.")
    @Profile("production")
    public DataSource getProdDataSource() throws NamingException {

        //Create JNDI context:
        Context ctx = new InitialContext();

        //Access to Jva EE environment:
        Context env_ctx = (Context) ctx.lookup("java:comp/env");

        //Get data source object from JNDI
        DataSource ds = (DataSource) env_ctx.lookup("jdbc/webapp");

        //Mapping datasource:
        this.active_datasource = ds;

        //Return statement:
        return ds;

    }

    /*
     * Hibernate beans.
     */

    /**
     * Create Session Factory beans witch provides a hibernate sessions.
     * Main bean in hibernate configuration.
     * @return - session factory bean.
     */
    @Bean("hibernateSessionFactory")
    @Description("Local session factory.")
    @Scope("singleton")
    public LocalSessionFactoryBean getSessionFactory() {

        //Create LocalSessionFactoryBean object:
        LocalSessionFactoryBean session_factory = new LocalSessionFactoryBean();

        //Set parameters to them:
        session_factory.setDataSource(this.active_datasource);
        session_factory.setPackagesToScan("by.bsac.models"); //Packages to scan
        session_factory.setConfigLocation(this.application_context.getResource("classpath:hibernate.cfg.xml"));

        //Return statement:
        return session_factory;
    }

    /**
     * Create transaction dispatcher to allow transaction data access.
     * @return - Transaction dispatcher.
     */
    @Bean("transactionManager")
    @Description("Transaction manager bean")
    public HibernateTransactionManager transactionManager() {

        //Create TransactionManager object
        HibernateTransactionManager transaction_manager = new HibernateTransactionManager();

        //Set parameters to them:
        transaction_manager.setSessionFactory(getSessionFactory().getObject());

        //Return statement:
        return transaction_manager;
    }

    /*
     * Java Persistence API beans
     */

    @Bean
    @Description("Factory for entity managers")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {

        //Create object
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        //Set parameters
        emf.setDataSource(this.active_datasource);
        emf.setPackagesToScan("by.bsac.models");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return null;
    }

    /*
     * Data access beans
     */

    /**
     * Create new User DAO implementation bean.
     * @return - user DAO implementation bean.
     */
    @Bean("user_dao")
    @Description("Implementation of UserDao interface.")
    public UserDao getUserDaoImplementation() {

        //Return statement
        return new UserDaoImpl(getSessionFactory().getObject());

    }


}
