package com.spring.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class LoginAttemptService {

    private final int MAX_ATTEMPTS = 5;
    private final long BLOCK_TIME = 10 * 60 * 1000; // 10 min

    private Map<String, Integer> attempts = new ConcurrentHashMap<>();
    private Map<String, Long> blockedAt = new ConcurrentHashMap<>();

    public boolean isBlocked(String ip) {
        if (!blockedAt.containsKey(ip)) return false;

        long blockTime = blockedAt.get(ip);
        long now = System.currentTimeMillis();

        if (now - blockTime > BLOCK_TIME) {
            // unblock
            blockedAt.remove(ip);
            attempts.remove(ip);
            return false;
        }

        return true;
    }

    public void loginFailed(String ip) {
        int count = attempts.getOrDefault(ip, 0) + 1;
        attempts.put(ip, count);

        if (count >= MAX_ATTEMPTS) {
            blockedAt.put(ip, System.currentTimeMillis());
        }
    }

    public void loginSuccess(String ip) {
        attempts.remove(ip);
        blockedAt.remove(ip);
    }
}
