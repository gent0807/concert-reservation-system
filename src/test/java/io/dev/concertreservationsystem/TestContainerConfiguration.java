package io.dev.concertreservationsystem;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
public class TestContainerConfiguration {
    public static final MySQLContainer<?> MYSQL_CONTAINER;

    private static final GenericContainer<?> REDIS_CONTAINER_FOR_DISTRIBUTED_LOCK;

    private static final GenericContainer<?> REDIS_CONTAINER_FOR_CACHE;

    static {
        MYSQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                .withDatabaseName("hhplus")
                .withUsername("test")
                .withPassword("test");
        MYSQL_CONTAINER.start();

        REDIS_CONTAINER_FOR_DISTRIBUTED_LOCK = new GenericContainer<>(DockerImageName.parse("redis:7.4.2-alpine"))
                .withExposedPorts(6379);
        REDIS_CONTAINER_FOR_DISTRIBUTED_LOCK.start();

        REDIS_CONTAINER_FOR_CACHE = new GenericContainer<>(DockerImageName.parse("redis:7.4.2-alpine"));

        REDIS_CONTAINER_FOR_CACHE.start();

        System.setProperty("spring.datasource.url", MYSQL_CONTAINER.getJdbcUrl() + "?characterEncoding=UTF-8&serverTimezone=UTC");
        System.setProperty("spring.datasource.username", MYSQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", MYSQL_CONTAINER.getPassword());

        System.setProperty("spring.data.redis.host", REDIS_CONTAINER_FOR_DISTRIBUTED_LOCK.getHost());
        System.setProperty("spring.data.redis.port", REDIS_CONTAINER_FOR_DISTRIBUTED_LOCK.getMappedPort(6379).toString());
    }

    @Bean
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(MYSQL_CONTAINER.getJdbcUrl() + "?characterEncoding=UTF-8&serverTimezone=UTC");
        hikariConfig.setUsername(MYSQL_CONTAINER.getUsername());
        hikariConfig.setPassword(MYSQL_CONTAINER.getPassword());
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setMaximumPoolSize(200);
        hikariConfig.setMinimumIdle(60);
        hikariConfig.setIdleTimeout(10000);
        hikariConfig.setConnectionTimeout(10000);
        hikariConfig.setValidationTimeout(10000);
        hikariConfig.setLeakDetectionThreshold(10000);
        hikariConfig.setAutoCommit(false);

        return new HikariDataSource(hikariConfig);
    }

    @PreDestroy
    public void preDestroy() {
        if (MYSQL_CONTAINER.isRunning()) {
            MYSQL_CONTAINER.stop();
        }

        if (REDIS_CONTAINER_FOR_DISTRIBUTED_LOCK.isRunning()) {
            REDIS_CONTAINER_FOR_DISTRIBUTED_LOCK.stop();
        }

        if(REDIS_CONTAINER_FOR_CACHE.isRunning()){
            REDIS_CONTAINER_FOR_CACHE.stop();
        }

    }
}
