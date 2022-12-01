package com.example.repository;

import com.example.entity.Users;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class RedisRepositoryTest {

    @Autowired
    private RedisRepository repository;

    @Test
    void repoTest() {
        Users users = new Users();
        users.setId("id");
        users.setPassword("1234");
        repository.save(users);
        Users users1 = repository.findById("id").get();
        System.out.println("users1 = " + users1);
    }
}