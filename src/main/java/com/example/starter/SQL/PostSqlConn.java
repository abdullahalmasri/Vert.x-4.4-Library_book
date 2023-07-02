package com.example.starter.SQL;

import com.example.starter.Utils.ApplicationUtils;
import com.example.starter.Utils.ConfigUtils;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import java.util.Properties;

import static com.example.starter.Constants.ModelConstant.*;

@Slf4j
public class PostSqlConn{


  private PostSqlConn() {
  }

  public static PgPool buildDbClient(Vertx vertx) {
    final Properties properties = ConfigUtils.getInstance().getProperties();
    final PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(Integer.parseInt(properties.getProperty(PORT_CONFIG)))
      .setHost(properties.getProperty(HOST_CONFIG))
      .setDatabase(properties.getProperty(DATABASE_CONFIG))
      .setUser(properties.getProperty(USERNAME_CONFIG))
      .setPassword(properties.getProperty(PASSWORD_CONFIG));

    final PoolOptions poolOptions = new PoolOptions().setMaxSize(ApplicationUtils.numberOfAvailableCores());
    log.info("database are set and the config is [{}]",connectOptions);
    return PgPool.pool(vertx, connectOptions, poolOptions);
  }

  public static Configuration buildMigrationsConfiguration() {
    final Properties properties = ConfigUtils.getInstance().getProperties();
    log.info("the configuration is [{}]",properties);

    final String url = "jdbc:postgresql://" + properties.getProperty(HOST_CONFIG) + ":" + properties.getProperty(PORT_CONFIG) + "/" + properties.getProperty(DATABASE_CONFIG);

    return new FluentConfiguration().dataSource(url, properties.getProperty(USERNAME_CONFIG), properties.getProperty(PASSWORD_CONFIG));
  }

}

