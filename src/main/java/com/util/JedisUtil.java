package com.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	
	// Singleton單例模式
    private static JedisPool pool = null; // 宣告一個靜態的JedisPool對象pool，初始值為null

    private JedisUtil() {
        // 私有的建構子，防止外部直接實例化這個類別
    }
	
	public static JedisPool getJedisPool() {
        // double lock
        if (pool == null) { // 檢查pool是否為null
            synchronized(JedisUtil.class) { // 使用synchronized關鍵字確保只有一個執行緒可以進入
                if (pool == null) { // 再次檢查pool是否為null
                    JedisPoolConfig config = new JedisPoolConfig(); // 創建一個JedisPoolConfig對象
                    config.setMaxTotal(8); // 設置最大連接數為8
                    config.setMaxIdle(8); // 設置最大空閒連接數為8
                    config.setMaxWaitMillis(10000); // 設置最大等待時間為10000毫秒
                    pool = new JedisPool(config, "localhost", 6379); // 創建一個新的JedisPool實例
                }
            }
        }
        return pool; // 返回JedisPool實例
    }

    public static void shutdownJedisPool() {
        if (pool != null) // 如果pool不為null
            pool.destroy(); // 關閉JedisPool，釋放資源
    }
}
    
