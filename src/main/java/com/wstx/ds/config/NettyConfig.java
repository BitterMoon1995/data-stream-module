package com.wstx.ds.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:netty.properties"})
public class NettyConfig {
    @Value("${sf.port}")
    private int sfPort;

    public int getSfPort() {
        return sfPort;
    }

    public void setSfPort(int sfPort) {
        this.sfPort = sfPort;
    }
}
