package com.chenluo.service;

import com.chenluo.data.dto.ConsumedMessage;
import com.chenluo.data.repo.ConsumedMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ConsumedMessageService {
    @Autowired
    private ConsumedMessageRepository repository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void increament(UUID uuid) {
        ConsumedMessage message = repository.findByUuid(uuid.toString());
        ConsumedMessage updatedMessage = new ConsumedMessage(message.id, message.uuid, message.count + 1, message.success);
        repository.save(updatedMessage);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void increamentByLock(UUID uuid) {
        ConsumedMessage message = repository.selectForUpdate(uuid.toString());
        ConsumedMessage updatedMessage = new ConsumedMessage(message.id, message.uuid, message.count + 1, message.success);
        repository.save(updatedMessage);
    }
}
