package by.bsac.data;

import by.bsac.data.dao.FriendsDao;
import by.bsac.data.dao.FriendsDaoImpl;
import by.bsac.data.dao.UserDao;
import by.bsac.data.dao.UserDaoImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.util.Properties;

/**
 * Configuration class for data manipulating.
 * Define JDBC resources (Data sources), Hibernate local session factory.
 * JPA Entity manager factory. Transaction managers for both JPA and Hibernate orm solutions,
 * DAO Beans.
 */
@Configuration
@EnableTransactionManagement
//Import database.properties definition class
@PropertySource("classpath:configuration/database.properties")
public class DataConfiguration{

    @Resource(name = "data_source")
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

    @Value("${hibernate.dialect}")
    private String hibernate_dialect;

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
    @Profile("DEVELOPMENT")
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
    @Profile("PRODUCTION")
    public DataSource getProdDataSource() throws NamingException {

        //Create JNDI context:
        Context ctx = new InitialContext();

        //Access to Jva EE environment:
        Context env_ctx = (Context) ctx.lookup("java:comp/env");

        //Get data source object from JNDI
        //Return statement:
        return (DataSource) env_ctx.lookup("jdbc/webapp");

    }

    private Properties JpaHibernateProperties() {

        //Create properties:
        Properties prop = new Properties();

        //Set properties
        prop.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");

        //Return statement:
        return prop;
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
    @Profile("HIBERNATE")
    public LocalSessionFactoryBean getSessionFactory() {

        //Create LocalSessionFactoryBean object:
        LocalSessionFactoryBean session_factory = new LocalSessionFactoryBean();

        //Set parameters to them:
        session_factory.setDataSource(this.active_datasource);
        session_factory.setPackagesToScan("by.bsac.models"); //Packages to scan
        session_factory.setHibernateProperties(JpaHibernateProperties());


        //Return statement:
        return session_factory;
    }

    /**
     * Create transaction dispatcher to allow transaction data access.
     * @return - Transaction dispatcher.
     */
    @Bean("transactionManager")
    @Description("Transaction manager bean")
    @Profile("HIBERNATETransaction")
    public HibernateTransactionManager getHibernateTransactionManager() {

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

    @Bean("jpa_entity_manager_factory")
    @Description("Factory for entity managers")
    @Profile("JPA")
    @Scope("singleton")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {

        //Create object
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        //Set parameters
        emf.setDataSource(this.active_datasource);
        emf.setPackagesToScan("by.bsac.models");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(JpaHibernateProperties());
        return emf;
    }

    @Bean("transactionManager")
    @Description("JPA transaction manager")
    @Profile("JPA")
    public PlatformTransactionManager getJpaTransactionManager() {

        //Create object:
        JpaTransactionManager jpa_tr_mng= new JpaTransactionManager();

        //Set parameters to them
        jpa_tr_mng.setEntityManagerFactory(getEntityManagerFactory().getObject());

        //Return statement
        return jpa_tr_mng;
    }

    /*
     * Data access beans
     */

    @Bean(name = "user_dao")
    @Description("User DAO implementation")
    public UserDao getUserDaoImplementation() {

        //Return statement
        return new UserDaoImpl();

    }

    @Bean(name = "friends_dao")
    @Description("Friends DAO implementation")
    public FriendsDao getFriendsDaoUserImplementation() {
        return new FriendsDaoImpl();
    }

}
