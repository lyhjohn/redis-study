package com.example.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
@ToString
public class Users extends UserBaseEntity{

    @Id
    private String id;
    private String password;

}

