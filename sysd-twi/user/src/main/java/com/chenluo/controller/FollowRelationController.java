package com.chenluo.controller;


import com.chenluo.entity.FollowRelation;
import com.chenluo.repo.FollowRelationRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FollowRelationController {
    private final FollowRelationRepo followRelationRepo;

    public FollowRelationController(FollowRelationRepo followRelationRepo) {
        this.followRelationRepo = followRelationRepo;
    }

    @GetMapping("getFollower")
    public List<String> getFollower(@RequestParam String uid) {
        List<FollowRelation> relations = followRelationRepo.findFollowRelationsByFolloweeUid(uid);
        return relations.stream().map(it -> it.followerUid).collect(Collectors.toList());
    }

    @GetMapping("getFollowee")
    public List<String> getFollowee(@RequestParam String uid) {
        List<FollowRelation> relations = followRelationRepo.findFollowRelationsByFollowerUid(uid);
        return relations.stream().map(it -> it.followeeUid).collect(Collectors.toList());
    }
}
