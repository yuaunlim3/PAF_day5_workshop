package PAF.day5_workshop;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {
    private final Logger logger = Logger.getLogger(AppConfig.class.getName());

    // Get all the redis configuration into the class
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.database}")
    private int redisDatabse;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    public JedisConnectionFactory createFactory() {
        // Create a database configuration
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        // Sets the database - select 0
        config.setDatabase(redisDatabse);
        // Set the username and password if they are set
        if (!redisUsername.trim().equals("")) {
            logger.info("Setting Redis username and password");
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }

        // Create a connection to the database
        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();

        // Create a factory to connect to Redis
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        return jedisFac;
    }

    @Bean("redis-0")
    public RedisTemplate<String, String> createRedisConnection() {
        JedisConnectionFactory jedisConnectionFactory = createFactory();
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        return template;
    }
}
