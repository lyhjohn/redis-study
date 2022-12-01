package com.example.service;

import com.example.entity.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@Rollback(value = false)
@SpringBootTest
class RedisServiceTest {

    @Autowired
    RedisService redisService;

    private Users users;

    @BeforeEach
    private void init() {
        users = new Users();
        users.setId("userA");
        users.setPassword("1234");
    }

    @Test
    void saveString() {
        redisService.saveString("string", "stringValue");
    }

    @Test
    void saveUser() throws JsonProcessingException {
        redisService.saveUsers(users);
    }

    @Test
    void saveSet() {
        redisService.saveSet("key", "1", "2", "3", "4");
    }

    @Test
    void saveHash() {
        redisService.saveHash("keyA", "hashKey1", "value1");
        redisService.saveHash("keyB", "hashKey2", "value2");
    }
}