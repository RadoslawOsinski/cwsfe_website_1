package eu.com.cwsfe.cms.version;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Automatically updates database
 * <p>
 * Created by Radosław Osiński
 */
@Configuration
public class DbMigrationManager {

    @Autowired
    private DataSource dataSource;

    @Bean
    public DbMigrationManager updateCmsDatabaseSchema() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
        return new DbMigrationManager();
    }

}
