package cn.waitfor.mbga.config.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@EnableCaching
@Configuration
public class CacheConfig {

  public static final String VERIFY_CODE = "verifyCode";

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

    // Cache configuration for verifyCode
    cacheConfigurations.put(
        VERIFY_CODE,
        RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(1))
            .disableCachingNullValues());

    return RedisCacheManager.builder(connectionFactory)
        .withInitialCacheConfigurations(cacheConfigurations)
        .build();
  }
}
