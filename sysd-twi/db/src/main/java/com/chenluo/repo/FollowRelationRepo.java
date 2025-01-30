package com.chenluo.repo;

import com.chenluo.entity.FollowRelation;
import com.chenluo.entity.Twi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRelationRepo extends JpaRepository<FollowRelation, Long> {

    List<FollowRelation> findFollowRelationsByFolloweeUid(String followeeUid);

    List<FollowRelation> findFollowRelationsByFollowerUid(String followerUid);

}
