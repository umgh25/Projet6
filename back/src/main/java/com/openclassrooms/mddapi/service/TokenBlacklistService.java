package com.openclassrooms.mddapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service to manage JWT token blacklist for logout functionality
 * Uses a ConcurrentHashMap to store invalidated tokens with their expiration times
 */
@Slf4j
@Service
public class TokenBlacklistService {

    // Store blacklisted tokens with their expiration time
    private final Map<String, Instant> blacklistedTokens = new ConcurrentHashMap<>();

    /**
     * Add a token to the blacklist
     * 
     * @param token the JWT token to blacklist
     * @param expirationTime the token expiration time
     */
    public void blacklistToken(String token, Instant expirationTime) {
        log.info("Adding token to blacklist");
        blacklistedTokens.put(token, expirationTime);
        cleanupExpiredTokens();
    }

    /**
     * Check if a token is blacklisted
     * 
     * @param token the JWT token to check
     * @return true if the token is blacklisted, false otherwise
     */
    public boolean isBlacklisted(String token) {
        cleanupExpiredTokens();
        boolean isBlacklisted = blacklistedTokens.containsKey(token);
        if (isBlacklisted) {
            log.warn("Token is blacklisted");
        }
        return isBlacklisted;
    }

    /**
     * Remove expired tokens from the blacklist to prevent memory leaks
     * This method is called automatically when checking or adding tokens
     */
    private void cleanupExpiredTokens() {
        Instant now = Instant.now();
        blacklistedTokens.entrySet().removeIf(entry -> entry.getValue().isBefore(now));
    }

    /**
     * Get the number of tokens currently in the blacklist (for monitoring)
     * 
     * @return number of blacklisted tokens
     */
    public int getBlacklistSize() {
        cleanupExpiredTokens();
        return blacklistedTokens.size();
    }
}
