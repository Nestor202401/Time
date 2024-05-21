package com.movie.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieJDBCDAO implements MovieDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tia101_g2?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO movie (movie_name,movie_rating,director,actor,release_date,end_date,runtime,introduction) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT movie_id,movie_name,movie_rating,director,actor,release_date,end_date,runtime,introduction FROM movie order by movie_id";
	private static final String GET_ONE_STMT = 
		"SELECT movie_id,movie_name,movie_rating,director,actor,release_date,end_date,runtime,introduction FROM movie where movie_id = ?";
	private static final String DELETE = 
		"DELETE FROM movie where movie_id = ?";
	private static final String UPDATE = 
		"UPDATE movie set movie_name=?, movie_rating=?, director=?, actor=?, release_date=?, end_date=?, runtime=?,introduction=? where movie_id = ?";
	private static final String GET_HOT_MOVIE = 
		"SELECT * FROM movie WHERE NOW() BETWEEN release_date AND end_date";
	private static final String OUT_OF_THEATERS = 
		"SELECT * FROM movie WHERE end_date < CURDATE()";
	private static final String COMING_SOON = 
			"SELECT * FROM movie WHERE release_date > CURDATE()";
	
	@Override
	public void insert(MovieVO movieVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, movieVO.getMovieName());
			pstmt.setInt(2, movieVO.getMovieRating());
			pstmt.setString(3, movieVO.getDirector());
			pstmt.setString(4, movieVO.getActor());
			pstmt.setDate(5, movieVO.getReleaseDate());
			pstmt.setDate(6, movieVO.getEndDate());
			pstmt.setInt(7, movieVO.getRuntime());
			pstmt.setString(8, movieVO.getIntroduction());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(MovieVO movieVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(9, movieVO.getMovieId());
			pstmt.setString(1, movieVO.getMovieName());
			pstmt.setInt(2, movieVO.getMovieRating());
			pstmt.setString(3, movieVO.getDirector());
			pstmt.setString(4, movieVO.getActor());
			pstmt.setDate(5, movieVO.getReleaseDate());
			pstmt.setDate(6, movieVO.getEndDate());
			pstmt.setInt(7, movieVO.getRuntime());
			pstmt.setString(8, movieVO.getIntroduction());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer movieId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, movieId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public MovieVO findByPrimaryKey(Integer movieId) {

		MovieVO movieVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, movieId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// movieVO 也稱為 Domain objects
				movieVO = new MovieVO();
				movieVO.setMovieId(rs.getInt("movie_Id"));
				movieVO.setMovieName(rs.getString("movie_Name"));
				movieVO.setMovieRating(rs.getInt("movie_Rating"));
				movieVO.setDirector(rs.getString("director"));
				movieVO.setActor(rs.getString("actor"));
				movieVO.setReleaseDate(rs.getDate("release_Date"));
				movieVO.setEndDate(rs.getDate("end_Date"));
				movieVO.setRuntime(rs.getInt("runtime"));
				movieVO.setIntroduction(rs.getString("introduction"));
				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return movieVO;
	}

	@Override
	public List<MovieVO> getAll() {
		List<MovieVO> list = new ArrayList<MovieVO>();
		MovieVO movieVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				movieVO = new MovieVO();
				movieVO.setMovieId(rs.getInt("movie_Id"));
				movieVO.setMovieName(rs.getString("movie_Name"));
				movieVO.setMovieRating(rs.getInt("movie_Rating"));
				movieVO.setDirector(rs.getString("director"));
				movieVO.setActor(rs.getString("actor"));
				movieVO.setReleaseDate(rs.getDate("release_Date"));
				movieVO.setEndDate(rs.getDate("end_Date"));
				movieVO.setRuntime(rs.getInt("runtime"));
				movieVO.setIntroduction(rs.getString("introduction"));
				list.add(movieVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public List<MovieVO> findHotMovie() {
		List<MovieVO> list = new ArrayList<MovieVO>();
		MovieVO movieVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_HOT_MOVIE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				movieVO = new MovieVO();
				movieVO.setMovieId(rs.getInt("movie_Id"));
				movieVO.setMovieName(rs.getString("movie_Name"));
				movieVO.setMovieRating(rs.getInt("movie_Rating"));
				movieVO.setDirector(rs.getString("director"));
				movieVO.setActor(rs.getString("actor"));
				movieVO.setReleaseDate(rs.getDate("release_Date"));
				movieVO.setEndDate(rs.getDate("end_Date"));
				movieVO.setRuntime(rs.getInt("runtime"));
				movieVO.setIntroduction(rs.getString("introduction"));
				list.add(movieVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	public List<MovieVO> findOutMovie() {
		List<MovieVO> list = new ArrayList<MovieVO>();
		MovieVO movieVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(OUT_OF_THEATERS);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				movieVO = new MovieVO();
				movieVO.setMovieId(rs.getInt("movie_Id"));
				movieVO.setMovieName(rs.getString("movie_Name"));
				movieVO.setMovieRating(rs.getInt("movie_Rating"));
				movieVO.setDirector(rs.getString("director"));
				movieVO.setActor(rs.getString("actor"));
				movieVO.setReleaseDate(rs.getDate("release_Date"));
				movieVO.setEndDate(rs.getDate("end_Date"));
				movieVO.setRuntime(rs.getInt("runtime"));
				movieVO.setIntroduction(rs.getString("introduction"));
				list.add(movieVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	public List<MovieVO> comingSoonMovie() {
		List<MovieVO> list = new ArrayList<MovieVO>();
		MovieVO movieVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(COMING_SOON);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				movieVO = new MovieVO();
				movieVO.setMovieId(rs.getInt("movie_Id"));
				movieVO.setMovieName(rs.getString("movie_Name"));
				movieVO.setMovieRating(rs.getInt("movie_Rating"));
				movieVO.setDirector(rs.getString("director"));
				movieVO.setActor(rs.getString("actor"));
				movieVO.setReleaseDate(rs.getDate("release_Date"));
				movieVO.setEndDate(rs.getDate("end_Date"));
				movieVO.setRuntime(rs.getInt("runtime"));
				movieVO.setIntroduction(rs.getString("introduction"));
				list.add(movieVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<MovieVO> getAll(Map<String, String[]> map) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		MovieVO movieVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String finalSQL = "select * from movie "
			          + jdbcUtil_CompositeQuery_Movie.get_WhereCondition(map)
			          + "order by movie_id";
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				movieVO = new MovieVO();
				movieVO.setMovieId(rs.getInt("movie_Id"));
				movieVO.setMovieName(rs.getString("movie_Name"));
				movieVO.setMovieRating(rs.getInt("movie_Rating"));
				movieVO.setDirector(rs.getString("director"));
				movieVO.setActor(rs.getString("actor"));
				movieVO.setReleaseDate(rs.getDate("release_Date"));
				movieVO.setEndDate(rs.getDate("end_Date"));
				movieVO.setRuntime(rs.getInt("runtime"));
				movieVO.setIntroduction(rs.getString("introduction"));
				list.add(movieVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	public static void main(String[] args) {

		MovieJDBCDAO dao = new MovieJDBCDAO();

		// 新增(新增內容為修改)
//		MovieVO movieVO1 = new MovieVO();
//		movieVO1.setMovieName("MANAGER");
//		movieVO1.setMovieRating("");
//		movieVO1.setDirector("");
//		movieVO1.setActor("");
//		movieVO1.setReleaseDate("");
//		movieVO1.setEndDate();
//		movieVO1.setRuntime(10);
//		movieVO1.setIntroduction(10);
//		dao.insert(movieVO1);
		
		

		// 修改
		
//		MovieVO movieVO2 = new MovieVO();
//		movieVO2.setMovieId("MANAGER");
//		movieVO2.setMovieName("MANAGER");
//		movieVO2.setMovieRating(java.sql.Date.valueOf("2005-01-01"));
//		movieVO2.setDirector(new Double(50000));
//		movieVO2.setActor(new Double(500));
//		movieVO2.setReleaseDate(10);
//		movieVO2.setEndDate(10);
//		movieVO2.setRuntime(10);
//		movieVO2.setIntroduction(10);
//		dao.update(movieVO2);

		// 刪除
//		dao.delete(7014);

		// 查詢
//		MovieVO movieVO3 = dao.findByPrimaryKey(7001);
//		System.out.print(movieVO3.getMovieId() + ",");
//		System.out.print(movieVO3.getMovieName() + ",");
//		System.out.print(movieVO3.getMovieRating() + ",");
//		System.out.print(movieVO3.getDirector() + ",");
//		System.out.print(movieVO3.getActor() + ",");
//		System.out.print(movieVO3.getReleaseDate() + ",");
//		System.out.print(movieVO3.getEndDate() + ",");
//		System.out.print(movieVO3.getRuntime() + ",");
//		System.out.println(movieVO3.getIntroduction());
//		System.out.println("---------------------");
		
		

		// 查詢
		List<MovieVO> list = dao.getAll();
		for (MovieVO aMovie : list) {
			System.out.print(aMovie.getMovieId() + ",");
			System.out.print(aMovie.getMovieName() + ",");
			System.out.print(aMovie.getMovieRating() + ",");
			System.out.print(aMovie.getDirector() + ",");
			System.out.print(aMovie.getActor() + ",");
			System.out.print(aMovie.getReleaseDate() + ",");
			System.out.print(aMovie.getEndDate()+ ",");
			System.out.print(aMovie.getRuntime()+ ",");
			System.out.print(aMovie.getIntroduction());
			System.out.println();
			
		}
	}
}