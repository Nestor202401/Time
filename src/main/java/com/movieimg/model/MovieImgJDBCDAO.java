package com.movieimg.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieImgJDBCDAO implements MovieImgDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tia101_g2?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO movie_img (movie_id,movie_img_name,movie_img_file) VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT movie_img_id,movie_id,movie_img_name,movie_img_file FROM movie_img order by movie_img_id";
	private static final String GET_ONE_STMT = 
		"SELECT movie_img_id,movie_id,movie_img_name,movie_img_file FROM movie_img where movie_img_id = ?";
	private static final String DELETE = 
		"DELETE FROM movie_img where movie_img_id = ?";
	private static final String UPDATE = 
		"UPDATE movie_img set movie_id=?, movie_img_name=?, movie_img_file=? where movie_img_id = ?";
	private static final String KEYWORD = 
		"SELECT movie_img_id, mi.movie_id, movie_img_name, movie_img_file " +
        "FROM movie_img mi " +
        "JOIN movie m ON mi.movie_id = m.movie_id " +
        "WHERE m.movie_name LIKE ?";
	private static final String GET_FIRST_IMAGE = 
		"SELECT movie_img_file FROM movie_img WHERE movie_id = ? ORDER BY movie_img_id ASC LIMIT 1";
    private static final String GET_IMG_BY_MOVIEID = 
    	"SELECT * FROM movie_img WHERE movie_id = ?";

	
	
	@Override
	public void insert(MovieImgVO movieImgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, movieImgVO.getMovieId());
			pstmt.setString(2, movieImgVO.getMovieImgName());
			pstmt.setString(3, movieImgVO.getMovieImgFile());
			
			

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
	public void update(MovieImgVO movieImgVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, movieImgVO.getMovieId());
			pstmt.setString(2, movieImgVO.getMovieImgName());
			pstmt.setString(3, movieImgVO.getMovieImgFile());
			pstmt.setInt(4, movieImgVO.getMovieImgId());
			

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
	public void delete(Integer movieImgId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, movieImgId);

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
	public MovieImgVO findByPrimaryKey(Integer movieImgId) {

		MovieImgVO movieImgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, movieImgId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				movieImgVO = new MovieImgVO();
				movieImgVO.setMovieImgId(rs.getInt("movie_img_id"));
				movieImgVO.setMovieId(rs.getInt("movie_id"));
				movieImgVO.setMovieImgName(rs.getString("movie_img_name"));
				movieImgVO.setMovieImgFile(rs.getString("movie_img_file"));
				
				
				
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
		return movieImgVO;
	}

	@Override
	public List<MovieImgVO> getAll() {
		List<MovieImgVO> list = new ArrayList<MovieImgVO>();
		MovieImgVO movieImgVO = null;

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
				movieImgVO = new MovieImgVO();
				movieImgVO.setMovieImgId(rs.getInt("movie_img_id"));
				movieImgVO.setMovieId(rs.getInt("movie_id"));
				movieImgVO.setMovieImgName(rs.getString("movie_img_name"));
				movieImgVO.setMovieImgFile(rs.getString("movie_img_file"));
				
				list.add(movieImgVO); // Store the row in the list
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
	public List<MovieImgVO> getKeyword(String keyword) {
		List<MovieImgVO> list = new ArrayList<MovieImgVO>();
		MovieImgVO movieImgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(KEYWORD);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				movieImgVO = new MovieImgVO();
				movieImgVO.setMovieImgId(rs.getInt("movie_img_id"));
				movieImgVO.setMovieId(rs.getInt("movie_id"));
				movieImgVO.setMovieImgName(rs.getString("movie_img_name"));
				movieImgVO.setMovieImgFile(rs.getString("movie_img_file"));
				
				list.add(movieImgVO); // Store the row in the list
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
    public String getFirstImageByMovieId(Integer movieId) {
        String movieImgFile = null;
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(GET_FIRST_IMAGE)) {

            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    movieImgFile = rs.getString("movie_img_file");
                }
            }

        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        }
        return movieImgFile;
    }
	
	public List<MovieImgVO> getImgByMovieId(Integer movieId) {
		List<MovieImgVO> list = new ArrayList<MovieImgVO>();
		MovieImgVO movieImgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_IMG_BY_MOVIEID);
			pstmt.setInt(1, movieId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				movieImgVO = new MovieImgVO();
				movieImgVO.setMovieImgId(rs.getInt("movie_img_id"));
				movieImgVO.setMovieId(rs.getInt("movie_id"));
				movieImgVO.setMovieImgName(rs.getString("movie_img_name"));
				movieImgVO.setMovieImgFile(rs.getString("movie_img_file"));
				
				list.add(movieImgVO); // Store the row in the list
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
	

	
}