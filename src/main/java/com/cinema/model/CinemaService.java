package com.cinema.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.movie.model.MovieVO;
import com.movieimg.model.MovieImgVO;
import com.movietime.model.MovieTimeVO;

import java.util.Comparator;

public class CinemaService {

	private CinemaDAO dao;

	public CinemaService() {
		dao = new CinemaDAO();
	}

	// 新增sql影廳座位表模板
	public void addSeatMap(String cinemaName, int rows, int columns) {
		List<CinemaVO> seats = new ArrayList<>();
		for (int col = 1; col <= columns; col++) {
			for (int row = 1; row <= rows; row++) {

				CinemaVO seat = new CinemaVO();
				seat.setCinemaName(cinemaName);
				seat.setSeatRow(row);
				seat.setSeatColumn(String.valueOf((char) ('A' + col - 1))); // 將列轉換為字母
				seat.setSeatStatus(0); // 初始化座位狀態為可用
				seat.setSeatNumber((char) ('A' + col - 1) + String.valueOf(row));
				seats.add(seat);
			}
		}
		dao.insert(seats);
	}

	public void updateSeatStatusBatch(String cinemaName, List<String> seatNumbers, int newStatus) {
		dao.updateSeatStatusBatch(cinemaName, seatNumbers, newStatus);
	}

	

	public List<CinemaVO> getAllCinemas() {
		return dao.getAllCinemas();
	}

	// 獲取所有影廳類型
	public List<CinemaVO> getCinemaNamesType() {
		return dao.getCinemaType();
	}

	// 從 SQL 數據庫獲取影廳模板數據
	public List<CinemaVO> getCinemaTemplate(String cinemaName) {
		return dao.findByCinemaName(cinemaName);
	}

	// 根據影廳ID獲取影廳名稱
	public String getCinemaNameById(Integer cinemaId) {
		CinemaVO cinema = dao.findByCinemaId(cinemaId);
		return cinema != null ? cinema.getCinemaName() : null;
	}

	// 為新場次生成專屬座位表並保存到 Redis，設置TTL為30天
	public void createSessionSeats(String sessionId, String cinemaName) {
		List<CinemaVO> templateSeats = getCinemaTemplate(cinemaName);
		List<CinemaVO> sessionSeats = new ArrayList<>();

		for (CinemaVO templateSeat : templateSeats) {
			CinemaVO sessionSeat = new CinemaVO();
			sessionSeat.setCinemaId(null); // 新的場次座位不需要原來的ID
			sessionSeat.setCinemaName(cinemaName);
			sessionSeat.setSeatRow(templateSeat.getSeatRow());
			sessionSeat.setSeatColumn(templateSeat.getSeatColumn());
//			sessionSeat.setSeatStatus(0); // 初始化座位狀態為可用
            sessionSeat.setSeatStatus(templateSeat.getSeatStatus());
			sessionSeat.setSeatNumber(templateSeat.getSeatNumber());
			sessionSeats.add(sessionSeat);
		}

		// 保存 sessionSeats 到 Redis 並設置TTL為30天
		RedisUtil.saveSessionSeats(sessionId, sessionSeats, 30);
	}

	// 從 Redis 獲取特定場次的座位表，並按座位號排序
	public List<Map<String, String>> getSessionSeats(String sessionId) {
		// 获取所有座位键
		Set<String> seatKeys = RedisUtil.getSessionSeatKeys(sessionId);
		List<Map<String, String>> seats = new ArrayList<>();

		for (String seatKey : seatKeys) {
			Map<String, String> seatData = RedisUtil.getSessionSeat(seatKey);
			long ttl = RedisUtil.getSeatTTL(seatKey);
			seatData.put("ttl", String.valueOf(ttl));
			seats.add(seatData);
		}

		// 按座位號排序
		seats.sort(Comparator.comparing(seat -> seat.get("seatNumber")));

		return seats;
	}

	// 返回字串用於下拉選單
	public List<String> getUniqueCinemaNames() {
		List<CinemaVO> cinemas = dao.getAllCinemas();
		return cinemas.stream().map(CinemaVO::getCinemaName).distinct().collect(Collectors.toList());
	}

	public List<CinemaVO> getUniqueCinemas() {
		List<CinemaVO> cinemas = dao.getAllCinemas();
		return cinemas.stream()
				.collect(Collectors.collectingAndThen(Collectors.toMap(CinemaVO::getCinemaName, c -> c, (c1, c2) -> c1),
						map -> map.values().stream().collect(Collectors.toList())));
	}

	// 更新redis座位狀態
	public void updateSeatStatus(String sessionId, String seatNumber, int status) {
		RedisUtil.updateSeatStatus(sessionId, seatNumber, status);
	}
}
