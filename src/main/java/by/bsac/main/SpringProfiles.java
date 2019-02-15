package by.bsac.main;

/**
 * This enumeration class define all Spring app profiles.
 * Included:
 *  1) Profiles for getting {@link javax.sql.DataSource} beans.
 *  2) Profiles for getting persistence realisation
 *      2.1) JPA persistence {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean} beans.
 *      2.2) Hibernate persistence {@link org.springframework.orm.hibernate5.LocalSessionFactoryBean } beans.
 *      2.3) Transaction managers.
 */
public enum SpringProfiles {

    // Profiles for getting DataSource bean
    DEVELOPMENT,
    PRODUCTION,

    //Profiles for persistence (JPA or HIBERNATE)
    HIBERNATE,
    JPA

}
