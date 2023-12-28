package com.chenluo.service;

import com.chenluo.entity.SimpleEntity;
import com.chenluo.repo.MyRepo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@Transactional
public class MyService {
    private final MyRepo myRepo;

    public MyService(MyRepo myRepo) {
        this.myRepo = myRepo;
    }

    public Mono<SimpleEntity> findById(int id) {
        return myRepo.findById(id);
    }

    public Mono<Void> removeById(int id) {
        Mono<Void> voidMono = myRepo.deleteById(id);
        return voidMono;
    }

    public Mono<SimpleEntity> findAndRemove(int id) {
        return findById(id).log().flatMap(e -> {
            if (e.id % 2 == 1) {
                removeById(e.id).subscribe();
                return Mono.just(e);
            } else {
                return myRepo.save(new SimpleEntity(0, 0)).map(s -> {
                    throw new RuntimeException("even id");
                });
            }
        });
    }
}
