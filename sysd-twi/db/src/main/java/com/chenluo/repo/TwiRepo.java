package com.chenluo.repo;

import com.chenluo.entity.Twi;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TwiRepo extends JpaRepository<Twi, Long> {
    public List<Twi> findTwisByUidInAndDateTimeLessThanOrderByDateTimeDesc(List<String> uid, ZonedDateTime dateTime, Limit limit);

}
