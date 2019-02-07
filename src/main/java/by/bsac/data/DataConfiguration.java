package by.bsac.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Configuration class for data manipulating.
 * Defines JDBC resources (Data sources).
 * DAO Beans.
 */
@Configuration
//Import database.properties definition class
@PropertySource("classpath:configuration/database.properties")
public class DataConfiguration {

    //Get values from 'database.property' file
    @Value("${db.url}")
    private String db_url;

    @Value("${db.driver}")
    private String db_driver_path;

    @Value("${db.username}")
    private String db_username;

    @Value("${db.userpass}")
    private String db_userpass;

    //JNDI DataSource
    @Resource(mappedName = "jdbc/webapp")
    private DataSource prod_data_source;


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
    public DataSource getProdDataSource() {

        //Return JNDI DataSource:
        return this.prod_data_source;

    }

}
