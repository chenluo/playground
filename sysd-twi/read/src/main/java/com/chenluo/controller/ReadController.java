package com.chenluo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@RestController
public class ReadController {
    private final JedisPool jedisPool;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${twi.fanout.url}")
    private String fanoutUrl;

    public ReadController(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @GetMapping("home")
    public List<String> home(@RequestParam String uid, @RequestParam(defaultValue = "0") int start) {
        try(Jedis jedis = jedisPool.getResource()) {
            if (0 == jedis.llen("tl#%s".formatted(uid))) {
                restTemplate.postForEntity(fanoutUrl+"/rebuild?uid={uid}", null, Void.class,
                        Map.of("uid", uid));
            }
            return jedis.lrange("tl#%s".formatted(uid), start, start + 9);
        }
    }
}
