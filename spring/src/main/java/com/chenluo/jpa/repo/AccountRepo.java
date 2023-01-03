package com.chenluo.jpa.repo;

import com.chenluo.jpa.dto.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepo extends CrudRepository<Account, Integer> {
    List<Account> findAccountsByCol1(String col1);
}
