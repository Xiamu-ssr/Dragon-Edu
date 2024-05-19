package org.dromara.es.config;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.es.domain.CourseBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, EsPageInfo<CourseBase>> caffeineCache() {
        return Caffeine.newBuilder()
            .initialCapacity(1)
            .maximumSize(10)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();
    }
}
