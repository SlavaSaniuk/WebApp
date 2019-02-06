package by.bsac.main;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *  Root application context configuration class.
 *  Parent context for all other context.
 *  Class contains all beans definition.
 */
@Configuration
// Import configuration class for data manipulation.
@Import(by.bsac.data.DataConfiguration.class)
public class RootAppContext {


}
