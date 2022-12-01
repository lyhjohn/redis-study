package com.example.controller;


import com.example.model.RequestForm;
import com.example.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @GetMapping
    public ResponseEntity<RequestForm> redisTest(@RequestBody RequestForm form) {
        System.out.println("form.getKey() = " + form.getKey());
        System.out.println("form.getValue() = " + form.getValue());
        redisService.saveString(form.getKey(), form.getValue());
        return ResponseEntity.ok(form);
    }


    @PostMapping("/stockSave")
    public void stockSave() {
        redisService.saveStock();
    }

    @PostMapping("/stock")
    public void stockTest() throws InterruptedException {
        redisService.minusStock();
    }
}
