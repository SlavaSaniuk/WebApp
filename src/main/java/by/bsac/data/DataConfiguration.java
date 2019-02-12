package by.bsac.data;

import by.bsac.data.dao.UserDao;
import by.bsac.data.dao.UserDaoImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
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

        //Return data source:
        return (DataSource) env_ctx.lookup("jdbc/webapp");

    }

    /*
     * Hibernate beans.
     */

    /**
     * Create Session Factory beans witch provides a hibernate sessions.
     * Main bean in hibernate configuration.
     * @param ds - Data source (depend on active profile)
     * @return - session factory bean.
     */
    @Bean("hibernateSessionFactory")
    @Description("Local session factory.")
    @Autowired
    @Scope("singleton")
    public LocalSessionFactoryBean getSessionFactory(DataSource ds) {

        //Create LocalSessionFactoryBean object:
        LocalSessionFactoryBean session_factory = new LocalSessionFactoryBean();

        //Set parameters to them:
        session_factory.setDataSource(ds);
        session_factory.setPackagesToScan("by.bsac.models"); //Packages to scan
        session_factory.setConfigLocation(this.application_context.getResource("classpath:hibernate.cfg.xml"));

        //Return statement:
        return session_factory;
    }

    /**
     * Create transaction dispatcher to allow transaction data access.
     * @param s - Hibernate session factory.
     * @return - Transaction dispatcher.
     */
    @Bean("transactionManager")
    @Description("Transaction manager bean")
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {

        //Create TransactionManager object
        HibernateTransactionManager transaction_manager = new HibernateTransactionManager();

        //Set parameters to them:
        transaction_manager.setSessionFactory(s);

        //Return statement:
        return transaction_manager;
    }

    /*
     * Data access beans
     */
    @Bean("user_dao")
    @Description("Implementation of UserDao interface.")
    public UserDao getUserDaoImplementation() {

        //Return statement
        return new UserDaoImpl();

    }


}
