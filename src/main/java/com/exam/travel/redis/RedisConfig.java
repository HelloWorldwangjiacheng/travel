package com.exam.travel.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author w1586
 * @Date 2020/3/3 2:19
 * @Cersion 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    private String host;

    private int port;

    private int timeout;

    private String password;

    private int poolMaxTotal;

    private int poolMaxIdle;

    private int poolMaxWait;
}


