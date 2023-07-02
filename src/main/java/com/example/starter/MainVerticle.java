package com.example.starter;

import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) {
//    final PgPool dbClient = PostSqlConn.buildDbClient(vertx);
//    final Configuration config = PostSqlConn.buildMigrationsConfiguration();
//    final Flyway flyway = new Flyway(config);
    deployMigrationVerticle(vertx)
      .flatMap(migrate->deployApiVerticle(vertx))
      .onSuccess(success -> LOGGER.info
        ("Verticle started successfully"))
      .onFailure(throwable -> LOGGER.error(throwable.getMessage()));



  }

  private Future<Void> deployMigrationVerticle(Vertx vertx) {
    final DeploymentOptions options = new DeploymentOptions()
      .setWorker(true)
      .setWorkerPoolName("migrations-worker-pool")
      .setInstances(1)
      .setWorkerPoolSize(1);

    return vertx.deployVerticle(MigrationVerticle.class.getName(), options)
      .flatMap(vertx::undeploy);
  }


  private Future<String> deployApiVerticle(Vertx vertx) {
    return vertx.deployVerticle(apiVerticle.class.getName());

  }
}
