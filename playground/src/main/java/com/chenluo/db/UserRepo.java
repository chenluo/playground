package com.chenluo.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("user_repo")
public interface UserRepo extends CrudRepository<UserDo, Integer> {
}
