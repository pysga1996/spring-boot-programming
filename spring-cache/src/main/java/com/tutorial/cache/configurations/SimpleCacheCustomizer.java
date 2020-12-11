package com.tutorial.cache.configurations;

import static java.util.Arrays.asList;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

/**
 * @author thanhvt
 * @project spring-cache-demo
 */
@Component
public class SimpleCacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(asList("users", "transactions"));
    }
}
