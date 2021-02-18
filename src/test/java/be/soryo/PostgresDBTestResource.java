package be.soryo;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import be.soryo.PostgresDBTestResource.ContainerInitializer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

@QuarkusTestResource(ContainerInitializer.class)
public class PostgresDBTestResource {
  public static class ContainerInitializer implements QuarkusTestResourceLifecycleManager {
    private PostgreSQLContainer<?> postgresContainer;

    @Override
    public Map<String, String> start() {
      this.postgresContainer = new PostgreSQLContainer<>("postgres").withDatabaseName("todos_quarkus");
      this.postgresContainer.start();
      return getConfigurationParameters();
    }

    private Map<String, String> getConfigurationParameters() {
      final Map<String, String> conf = new HashMap<>();
      
      conf.put("quarkus.datasource.postgresTodosFlyway.jdbc.url", this.postgresContainer.getJdbcUrl());
      conf.put("quarkus.datasource.postgresTodosFlyway.username", this.postgresContainer.getUsername());
      conf.put("quarkus.datasource.postgresTodosFlyway.username", this.postgresContainer.getPassword());

      String reactiveUrl = "postgresql://"+ this.postgresContainer.getHost() + ":" + this.postgresContainer.getFirstMappedPort() + "/todos_quarkus";
      conf.put("quarkus.datasource.postgresTodos.reactive.url", reactiveUrl);
      conf.put("quarkus.datasource.postgresTodos.username", this.postgresContainer.getUsername());
      conf.put("quarkus.datasource.postgresTodos.password", this.postgresContainer.getPassword());
      return conf;
    }

    @Override
      public void stop() {
        if (this.postgresContainer != null) {
          this.postgresContainer.close();
        }
      }
  }
}