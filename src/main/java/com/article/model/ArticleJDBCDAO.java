package com.article.model;

import java.util.*;
import java.sql.*;

public class ArticleJDBCDAO implements ArticleDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tia101_g2?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO article (article_Id,type_Id,member_Id,theme,article_Content,browse_Peoples,article_Status,release_Time) VALUES (default, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT article_id,type_id,member_id,theme,article_content,browse_peoples,article_status,release_time FROM article where article_Status in (0,1) order by article_id";
	private static final String GET_ONE_STMT = 
		"SELECT article_Id,type_Id,member_Id,theme,article_Content,browse_Peoples,article_Status,release_Time FROM article where article_Id = ?";
	private static final String DELETE = 
		"DELETE FROM article where article_Id = ?";
	private static final String UPDATE = 
		"UPDATE article set type_Id=?, member_Id=?, theme=?, article_Content=?, browse_Peoples=?, article_Status=?, release_Time=? where article_Id = ?";
	private static final String UPDATEBrowsePeoples = 
			"UPDATE article set browse_peoples = browse_peoples+1 where article_Id=?";
	private static final String UPDATEArticleStatus = 
			"UPDATE article set article_Status = 2 where article_Id=?";

	@Override
	public void insert(ArticleVO articleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, articleVO.getTypeId());
			pstmt.setInt(2, articleVO.getMemberId());
			pstmt.setString(3, articleVO.getTheme());
			pstmt.setString(4, articleVO.getArticleContent());
			pstmt.setInt(5, articleVO.getBrowsePeoples());
			pstmt.setInt(6, articleVO.getArticleStatus());
			pstmt.setTimestamp(7, articleVO.getReleaseTime());

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
	public void update(ArticleVO articleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, articleVO.getTypeId());
			pstmt.setInt(2, articleVO.getMemberId());
			pstmt.setString(3, articleVO.getTheme());
			pstmt.setString(4, articleVO.getArticleContent());
			pstmt.setInt(5, articleVO.getBrowsePeoples());
			pstmt.setInt(6, articleVO.getArticleStatus());
			pstmt.setTimestamp(7, articleVO.getReleaseTime());
			pstmt.setInt(8, articleVO.getArticleId());
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
	public void delete(Integer ArticleId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ArticleId);

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
	public ArticleVO findByPrimaryKey(Integer ArticleId) {

		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, ArticleId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				articleVO = new ArticleVO();
				articleVO.setArticleId(rs.getInt("article_id"));
				articleVO.setTypeId(rs.getInt("type_id"));
				articleVO.setMemberId(rs.getInt("member_id"));
				articleVO.setTheme(rs.getString("theme"));
				articleVO.setArticleContent(rs.getString("article_content"));
				articleVO.setBrowsePeoples(rs.getInt("browse_peoples"));
				articleVO.setArticleStatus(rs.getInt("article_status"));
				articleVO.setReleaseTime(rs.getTimestamp("release_time"));
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
		return articleVO;
	}

	@Override
	public List<ArticleVO> getAll() {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;

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
				articleVO = new ArticleVO();
				articleVO.setArticleId(rs.getInt("article_Id"));
				articleVO.setTypeId(rs.getInt("type_Id"));
				articleVO.setMemberId(rs.getInt("member_Id"));
				articleVO.setTheme(rs.getString("theme"));
				articleVO.setArticleContent(rs.getString("article_Content"));
				articleVO.setBrowsePeoples(rs.getInt("browse_Peoples"));
				articleVO.setArticleStatus(rs.getInt("article_Status"));
				articleVO.setReleaseTime(rs.getTimestamp("release_Time"));
				list.add(articleVO); // Store the row in the list
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
	public void updateBrowsePeoples(Integer articleId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATEBrowsePeoples);

			pstmt.setInt(1, articleId);
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
	public void updateArticleStatus(Integer articleId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATEArticleStatus);

			pstmt.setInt(1, articleId);
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

	public static void main(String[] args) {

		ArticleJDBCDAO dao = new ArticleJDBCDAO();

		// 新增
//		EmpVO empVO1 = new EmpVO();
//		empVO1.setEname("吳永志1");
//		empVO1.setJob("MANAGER");
//		empVO1.setHiredate(java.sql.Date.valueOf("2005-01-01"));
//		empVO1.setSal(new Double(50000));
//		empVO1.setComm(new Double(500));
//		empVO1.setDeptno(10);
//		dao.insert(empVO1);

		// 修改
//		EmpVO empVO2 = new EmpVO();
//		empVO2.setEmpno(7001);
//		empVO2.setEname("吳永志2");
//		empVO2.setJob("MANAGER2");
//		empVO2.setHiredate(java.sql.Date.valueOf("2002-01-01"));
//		empVO2.setSal(new Double(20000));
//		empVO2.setComm(new Double(200));
//		empVO2.setDeptno(20);
//		dao.update(empVO2);

		// 刪除
//		dao.delete(7014);

		// 查詢
//		EmpVO empVO3 = dao.findByPrimaryKey(7001);
//		System.out.print(empVO3.getEmpno() + ",");
//		System.out.print(empVO3.getEname() + ",");
//		System.out.print(empVO3.getJob() + ",");
//		System.out.print(empVO3.getHiredate() + ",");
//		System.out.print(empVO3.getSal() + ",");
//		System.out.print(empVO3.getComm() + ",");
//		System.out.println(empVO3.getDeptno());
//		System.out.println("---------------------");

		// 查詢
		List<ArticleVO> list = dao.getAll();
		for (ArticleVO aArt : list) {
			System.out.print(aArt.getArticleId() + ",");
			System.out.print(aArt.getTypeId() + ",");
			System.out.print(aArt.getMemberId() + ",");
			System.out.print(aArt.getTheme() + ",");
			System.out.print(aArt.getBrowsePeoples() + ",");
			System.out.print(aArt.getArticleStatus() + ",");
			System.out.print(aArt.getReleaseTime()+ ",");
			System.out.print(aArt.getArticleContent() );
			System.out.println();
		}
	}

	
}