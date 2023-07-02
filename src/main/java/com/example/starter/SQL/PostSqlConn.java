package com.example.starter.SQL;

import com.example.starter.Utils.ApplicationUtils;
import com.example.starter.Utils.ConfigUtils;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import java.util.Properties;

import static com.example.starter.Constants.ModelConstant.*;

/**
 * The type Post sql conn.
 */
public class PostSqlConn {
    private static final Logger log = LoggerFactory.getLogger(PostSqlConn.class);


    private PostSqlConn() {
    }

    /**
     * Build db client pg pool.
     *
     * @param vertx the vertx
     * @return the pg pool
     */
    public static PgPool buildDbClient(Vertx vertx) {
        final Properties properties = ConfigUtils.getInstance().getProperties();
        final PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(Integer.parseInt(properties.getProperty(PORT_CONFIG)))
                .setHost(properties.getProperty(HOST_CONFIG))
                .setDatabase(properties.getProperty(DATABASE_CONFIG))
                .setUser(properties.getProperty(USERNAME_CONFIG))
                .setPassword(properties.getProperty(PASSWORD_CONFIG));

        final PoolOptions poolOptions = new PoolOptions().setMaxSize(ApplicationUtils.numberOfAvailableCores());
        log.info("database are set and the config is " + connectOptions);
        return PgPool.pool(vertx, connectOptions, poolOptions);
    }

    /**
     * Build migrations configuration configuration.
     *
     * @return the configuration
     */
    public static Configuration buildMigrationsConfiguration() {
        final Properties properties = ConfigUtils.getInstance().getProperties();
        log.info("the configuration is " + properties);

        final String url = "jdbc:postgresql://" + properties.getProperty(HOST_CONFIG) + ":" + properties.getProperty(PORT_CONFIG) + "/" + properties.getProperty(DATABASE_CONFIG);

        return new FluentConfiguration().dataSource(url, properties.getProperty(USERNAME_CONFIG), properties.getProperty(PASSWORD_CONFIG));
    }

}

