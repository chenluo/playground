package com.chenluo.controller;

import com.chenluo.entity.Twi;
import com.chenluo.repo.TwiRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Limit;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ReadController {
    private final JedisPool jedisPool;
    private final TwiRepo twiRepo;
    private final RestTemplate restTemplate;

    @Value("${twi.fanout.url}")
    private String fanoutUrl;

    @Value("${twi.user.url}")
    private String userUrl;

    public ReadController(JedisPool jedisPool, TwiRepo twiRepo, RestTemplate restTemplate) {
        this.jedisPool = jedisPool;
        this.twiRepo = twiRepo;
        this.restTemplate = restTemplate;
    }

    @GetMapping("home")
    public List<String> home(@RequestParam String uid, @RequestParam(defaultValue = "0") int start) {
        List<String> followees = restTemplate.exchange(userUrl + "/getFollowee?uid={uid}", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<String>>(){}, Map.of("uid", uid)).getBody();
        List<Twi> twis = twiRepo.findTwisByUidInAndDateTimeLessThanOrderByDateTimeDesc(followees, ZonedDateTime.now(), Limit.of(100));
        return twis.stream().map(it -> it.tid).collect(Collectors.toList());
    }
    @GetMapping("home/cache")
    public List<String> homeCache(@RequestParam String uid, @RequestParam(defaultValue = "0") int start) {
        try(Jedis jedis = jedisPool.getResource()) {
            if (0 == jedis.llen("tl#%s".formatted(uid))) {
                restTemplate.postForEntity(fanoutUrl+"/rebuild?uid={uid}", null, Void.class,
                        Map.of("uid", uid));
            }
            return jedis.lrange("tl#%s".formatted(uid), start, start + 9);
        }
    }
}
