package org.dromara.es.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.es.domain.CourseBase;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

/**
 * 咖啡因配置
 * <BR/>
 * 因为ruoyi的redis也是cacheManager，注解也和caffeine几乎一样，所以es模块设置了caffeine为primary，redis的注解应该就用不了了。
 *
 * @author mumu
 * @date 2024/05/17
 */
@Configuration
public class CaffeineConfig extends CachingConfigurerSupport {

    @Primary
    @Bean(name = "caffeineCacheManager")
    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("hotCourseListCache");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineCacheBuilder(){
        return Caffeine.newBuilder()
            .initialCapacity(10)
            .maximumSize(100)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .weakKeys()
            .recordStats();
    }
}
