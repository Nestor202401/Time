package com.movie.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.DateTypeAdapter;
import redis.clients.jedis.Jedis;

public class RedisDAO {
	private Jedis jedis;
	private Gson gson;
	private static final int EXPIRE_TIME = 6000;

	public RedisDAO() {
		this.jedis = new Jedis("localhost", 6379);
		this.gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
	}

	public void saveMovies(List<MovieVO> movies) {
        String json = gson.toJson(movies);
        jedis.set("movies", json);
        jedis.expire("movies", EXPIRE_TIME);
        System.out.println("有執行到同步");
    }

	public List<MovieVO> getMovies() {
		String json = jedis.get("movies");
		System.out.println("redis查找全部方法使用");
		if (json == null) {
			return null;
		}
		JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
		List<MovieVO> movies = new ArrayList<>();
		for (JsonElement jsonElement : jsonArray) {
			MovieVO movie = gson.fromJson(jsonElement, MovieVO.class);
			movies.add(movie);
		}
		return movies;
	}

	public void deleteMovies() {
		jedis.del("movies");
	}
}
