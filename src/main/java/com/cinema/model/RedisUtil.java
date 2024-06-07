package com.cinema.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    private static Jedis jedis = new Jedis("localhost", 6379);

    // 存儲場次座位表到 Redis 並設置TTL
    public static void saveSessionSeats(String sessionId, List<CinemaVO> seats, int ttlDays) {
        for (CinemaVO seat : seats) {
            String key = "session:" + sessionId + ":seat:" + seat.getSeatNumber();
            Map<String, String> seatData = new HashMap<>();
            seatData.put("cinemaName", seat.getCinemaName());
            seatData.put("seatRow", String.valueOf(seat.getSeatRow()));
            seatData.put("seatColumn", seat.getSeatColumn());
            seatData.put("seatStatus", String.valueOf(seat.getSeatStatus()));
            seatData.put("seatNumber", seat.getSeatNumber()); 
            jedis.hmset(key, seatData);
            jedis.expire(key, ttlDays * 24 * 60 * 60); // 設置TTL為指定的天數（秒為單位）
        }
    }

    // 從 Redis 讀取場次座位表
    public static Map<String, String> getSessionSeat(String seatKey) {
        return jedis.hgetAll(seatKey);
    }

    // 獲取特定場次所有座位鍵
    public static Set<String> getSessionSeatKeys(String sessionId) {
        return jedis.keys("session:" + sessionId + ":seat:*");
    }
    
 // 獲取剩余的TTL時間（秒）
    public static long getSeatTTL(String seatKey) {
        return jedis.ttl(seatKey);
    }
    
    public static void updateSeatStatus(String sessionId, String seatNumber, int status) {
        String seatKey = "session:" + sessionId + ":seat:" + seatNumber;
        jedis.hset(seatKey, "seatStatus", String.valueOf(status));
    }

    
}
