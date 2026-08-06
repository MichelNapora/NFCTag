package com.nfctag.features.scan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Plafonne le nombre de scans qu'un même tag peut accepter par minute.
 */
@Component
public class ScanRateLimiter {

    @Value("${nfctag.scan-per-minute}") private int scansPerMinute;

    private final Map<UUID, Bucket> buckets = new ConcurrentHashMap<>();

    public void check(UUID tagId){
        Bucket bucket = this.buckets.computeIfAbsent(tagId, id -> new Bucket(this.scansPerMinute));
        if (!bucket.tryConsume()) {
            throw new TooManyScansException(this.scansPerMinute);
        }
    }

    private static final class Bucket {

        private final double capacity;
        private final double refillPerNano;
        private double tokens;
        private long lastRefill;

        private Bucket(double capacity){
            this.capacity = capacity;
            this.refillPerNano = capacity / (double) Duration.ofMinutes(1).toNanos();
            this.tokens = capacity;
            this.lastRefill = System.nanoTime();
        }

        private synchronized boolean tryConsume(){
            long now = System.nanoTime();
            this.tokens = Math.min(this.capacity, this.tokens + (now - this.lastRefill) * this.refillPerNano);
            this.lastRefill = now;

            if (this.tokens < 1) { return false; }
            this.tokens -= 1;
            return true;
        }
    }
}