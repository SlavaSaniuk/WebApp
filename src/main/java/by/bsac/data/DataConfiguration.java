package by.bsac.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Configuration class for data manipulating.
 * Defines JDBC resources (Data sources).
 * DAO Beans.
 */
@Configuration
//Import database.properties definition class
@PropertySource("classpath:configuration/database.properties")
public class DataConfiguration {

    /*
     *   Data sources beans definition.
     *   Called by @Resource annotation.
     *   Data sources for different profiles.
     */

    @Bean("data_source")
    @Description("DataSource for development profile.")
    @Profile("development")
    public DriverManagerDataSource getDevDataSource(
            @Value("db.url") String db_url, @Value("db.driver") String db_driver_name,
            @Value("db.username") String db_username, @Value("db.userpass") String db_user_password) {

        //Create DriverManagerDataSource object:
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Initialize data source:
        ds.setUrl(db_url);
        ds.setDriverClassName(db_driver_name);
        ds.setUsername(db_username);
        ds.setPassword(db_user_password);

        //Return configured data source:
        return ds;

    }

}
