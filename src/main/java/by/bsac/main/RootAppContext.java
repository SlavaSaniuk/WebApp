package by.bsac.main;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *  Root application context configuration class.
 *  Parent context for all other context.
 *  Class contains all beans definition.
 */
@Configuration
// Import configuration classes.
@Import({by.bsac.data.DataConfiguration.class, //Data manipulation class
            //by.bsac.services.ServicesConfiguration.class //Services configuration class
        })
public class RootAppContext {


}
