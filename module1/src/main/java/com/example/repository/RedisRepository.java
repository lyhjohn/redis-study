package com.example.repository;

import com.example.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<Users, String> {
}
