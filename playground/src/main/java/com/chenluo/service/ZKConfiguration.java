package com.chenluo.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "zookeeper")
public class ZKConfiguration {
    private String host;
    private String port;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
