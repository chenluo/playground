package com.chenluo.service;

import com.chenluo.entity.Twi;
import com.chenluo.repo.TwiRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Limit;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class FanoutService {
    private final JedisPool jedisPool;
    private final RestTemplate restTemplate = new RestTemplate();
    private final TwiRepo twiRepo;

    @Value("${twi.user.url}")
    private String userUrl;

    public FanoutService(JedisPool jedisPool, TwiRepo twiRepo) {
        this.jedisPool = jedisPool;
        this.twiRepo = twiRepo;
    }

    public String fanout(String uid, String tid) {
        List<String> followers = restTemplate.exchange(userUrl + "/getFollower?uid={uid}", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<String>>(){}, Map.of("uid", uid)).getBody();
        for (String follower : followers) {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.lpush("tl#%s".formatted(follower), tid);
                jedis.ltrim("tl#%s".formatted(follower), 0, 99);
            }
        }
        return "success";

    }
    public String rebuild(String uid) {
        List<String> followees = restTemplate.exchange(userUrl + "/getFollowee?uid={uid}", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<String>>(){}, Map.of("uid", uid)).getBody();
        List<Twi> twis = twiRepo.findTwisByUidInAndDateTimeLessThanOrderByDateTimeDesc(followees, ZonedDateTime.now(), Limit.of(100));
        if (!twis.isEmpty()) {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.lpush("tl#%s".formatted(uid), twis.stream().map(it -> it.tid).toList().toArray(new String[]{}));
                jedis.ltrim("tl#%s".formatted(uid), 0, 99);
            }
        }
        return "success";
    }
}
