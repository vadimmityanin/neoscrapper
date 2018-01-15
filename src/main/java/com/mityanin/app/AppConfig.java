package com.mityanin.app;

import lombok.Setter;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.configuration.BoltConnector;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties("embedded.db")
@Setter
public class AppConfig {

    private boolean enabled;
    private String auth;
    private String path;

    @Bean
    public GraphDatabaseService getEmbeddedDbEnabled() {
        GraphDatabaseService graphDb = null;
        if (enabled) {
            graphDb = new GraphDatabaseFactory()
                    .newEmbeddedDatabaseBuilder(new File(path ==null
                            ? System.getProperty("user.home") + File.separator +"Neo4jTempDB"
                            : path))
                    .setConfig(GraphDatabaseSettings.auth_enabled, auth)
                    .setConfig(new BoltConnector("bolt").enabled, "true")
                    .newGraphDatabase();
            Runtime.getRuntime().addShutdownHook(new Thread(graphDb::shutdown));
        }
        return graphDb;
    }
}
