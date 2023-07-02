package com.example.starter;

import com.example.starter.SQL.PostSqlConn;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;

public class MigrationVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(MigrationVerticle.class);
  @Override
  public void start(Promise<Void> promise) {
    final Configuration config = PostSqlConn.buildMigrationsConfiguration();
    final Flyway flyway = new Flyway(config);
    LOGGER.info("Starting migration");
    flyway.migrate();

    promise.complete();
  }
}
