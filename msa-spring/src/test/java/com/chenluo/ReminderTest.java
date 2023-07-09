package com.chenluo;

import com.chenluo.data.dto.Reminder;
import com.chenluo.data.repo.ReminderRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReminderTest {
    @Autowired
    private ReminderRepository reminderRepository;

    @BeforeAll
    public void setup() {
        reminderRepository.save(
                new Reminder(null, 1, "corp", 1, LocalDateTime.now(), "user", 1, "1",
                        String.valueOf(
                                LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() %
                                        10000000)));
        reminderRepository.save(
                new Reminder(null, 1, "corp", 2, LocalDateTime.now(), "user", 1, "1",
                        String.valueOf(
                                LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() %
                                        10000000)));
        reminderRepository.save(
                new Reminder(null, 1, "corp", 3, LocalDateTime.now(), "user", 1, "1",
                        String.valueOf(
                                LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() %
                                        10000000)));
        reminderRepository.save(
                new Reminder(null, 2, "corp", 2, LocalDateTime.now(), "user", 1, "1",
                        String.valueOf(
                                LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() %
                                        10000000)));
        reminderRepository.save(
                new Reminder(null, 2, "corp", 3, LocalDateTime.now(), "user", 1, "1",
                        String.valueOf(
                                LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() %
                                        10000000)));
        reminderRepository.save(
                new Reminder(null, 3, "corp", 3, LocalDateTime.now(), "user", 1, "1",
                        String.valueOf(
                                LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() %
                                        10000000)));
    }

    @AfterAll
    public void teardown() {
        reminderRepository.deleteAll();
    }

    @Test
    public void start() {

    }


    @Test
    public void testFindType1() {
        List<Reminder> results = reminderRepository.findType1("corp");
        assert results.size() == 1;
        assert results.get(0).getOutboxMsgId() == 1;
    }

    @Test
    public void testFindType2() {
        List<Reminder> results = reminderRepository.findType2("corp");
        assert results.size() == 1;
        assert results.get(0).getOutboxMsgId() == 2;
    }

    @Test
    public void testFindType3() {
        List<Reminder> results = reminderRepository.findType3("corp");
        assert results.size() == 1;
        assert results.get(0).getOutboxMsgId() == 3;


    }
}
