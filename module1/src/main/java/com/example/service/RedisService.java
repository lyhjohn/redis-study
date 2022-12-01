package com.example.service;

import com.example.entity.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
@RequiredArgsConstructor
//@Transactional
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedissonClient redissonClient;
    private final ObjectMapper objectMapper;

    public void saveStock() {
        redisTemplate.opsForValue().set("box", "100");
    }

    public void minusStock() throws InterruptedException {
        RLock lock = redissonClient.getLock("box:lock");

        try {
            boolean isLock = lock.tryLock(5, 5, TimeUnit.SECONDS);
            if (!isLock) {
                System.out.println("Lock 획득 실패");
                return;
            }
            int box = Integer.parseInt((String) Objects.requireNonNull(redisTemplate.opsForValue().get("box")));

            int curBox = box - 1;
            System.out.println("curBox = " + curBox);
            Thread.sleep(3000);
            redisTemplate.opsForValue().set("box", curBox + "");
        } catch (InterruptedException e) {
            System.out.println("Lock exception");
        } finally {
            lock.unlock();
        }
        System.out.println("unlock");
    }

    public void saveString(String key, Object value) {
        RLock lock = redissonClient.getLock(key + ":lock");

        // 2초가 돟안 Lock 획득을 못할 시 false 반환 | Lock 지속시간 5초
        // 언락 전에 커밋되는지 확인 필요
        try {
            boolean isLocked = lock.tryLock(4, 5, TimeUnit.SECONDS);
            if (!isLocked) {
                System.out.println("락 획득 실패");
                return;
            }
            redisTemplate.opsForValue().set(key, value);
        } catch (InterruptedException e) {
            throw new RuntimeException("락 취득 도중 에러 발생");
        } finally {
            lock.unlock();
        }
    }

    public void saveSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
        Set<Object> members = redisTemplate.opsForSet().members(key);
        System.out.println("members = " + members);
    }

    public void saveUsers(Users users) throws JsonProcessingException {
        RLock lock = redissonClient.getLock(users.getId());
        String user = objectMapper.writeValueAsString(users);
        redisTemplate.opsForValue().set(users.getId(), user);
    }

    public void saveHash(String key, Object hashKey, Object values) {
        redisTemplate.opsForHash().put(key, hashKey, values);
        Object o = redisTemplate.opsForHash().get(key, hashKey);
        System.out.println("o = " + o);
    }
}
