package com.comments.model;

import java.util.*;
import java.sql.*;

public class CommentsJDBCDAO implements CommentsDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tia101_g2?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO comments (article_id,member_id,comment_content,comment_status,release_time) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT comment_id,article_id,member_id,comment_content,comment_status,release_time FROM comments order by comment_id";
	private static final String GET_ALL_BY_ARTICLE_ID_STMT = 
			"SELECT comment_id,article_id,member_id,comment_content,comment_status,release_time FROM comments WHERE article_id = ? order by comment_id";
	private static final String GET_ONE_STMT = 
		"SELECT comment_id,article_id,member_id,comment_content,comment_status,release_time FROM comments where comment_id = ?";
	private static final String DELETE = 
		"DELETE FROM article where comment_id = ?";
	private static final String UPDATE = 
		"UPDATE comments set article_id=?, member_id=?, comment_content=?, comment_status=?, release_time=? where comment_id = ?";
	private static final int CommentId = 0;

	@Override
	public void insert(CommentsVO commentsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, commentsVO.getArticleId());
			pstmt.setInt(2, commentsVO.getMemberId());
			pstmt.setString(3, commentsVO.getCommentContent());
			pstmt.setInt(4, commentsVO.getCommentStatus());
			pstmt.setTimestamp(5, commentsVO.getReleaseTime());
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
	public void update(CommentsVO commentsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			int parameterIndex = 1;
			pstmt.setInt(parameterIndex++, commentsVO.getArticleId());
			pstmt.setInt(parameterIndex++, commentsVO.getMemberId());
			pstmt.setString(parameterIndex++, commentsVO.getCommentContent());
			pstmt.setInt(parameterIndex++, commentsVO.getCommentStatus());
			pstmt.setTimestamp(parameterIndex++, commentsVO.getReleaseTime());
			pstmt.setInt(parameterIndex++, commentsVO.getCommentId());
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
	public void delete(Integer Comments) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, CommentId);

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
	public CommentsVO findByPrimaryKey(Integer CommentId) {

		CommentsVO commentsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, CommentId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				commentsVO = new CommentsVO();
				commentsVO.setCommentId(rs.getInt("comment_id"));
				commentsVO.setArticleId(rs.getInt("article_id"));
				commentsVO.setMemberId(rs.getInt("member_Id"));
				commentsVO.setCommentContent(rs.getString("comment_content"));
				commentsVO.setCommentStatus(rs.getInt("comment_status"));
				commentsVO.setReleaseTime(rs.getTimestamp("release_time"));
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
		return commentsVO;
	}

	@Override
	public List<CommentsVO> getAll() {
		List<CommentsVO> list = new ArrayList<CommentsVO>();
		CommentsVO commentsVO = null;

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
				commentsVO = new CommentsVO();
				commentsVO.setCommentId(rs.getInt("comment_id"));
				commentsVO.setArticleId(rs.getInt("article_id"));
				commentsVO.setMemberId(rs.getInt("member_id"));
				commentsVO.setCommentContent(rs.getString("comment_content"));
				commentsVO.setCommentStatus(rs.getInt("comment_status"));
				commentsVO.setReleaseTime(rs.getTimestamp("release_time"));
				list.add(commentsVO); // Store the row in the list
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
	public List<CommentsVO> getAllByArticleId(Integer articleId) {
		List<CommentsVO> list = new ArrayList<CommentsVO>();
		CommentsVO commentsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_BY_ARTICLE_ID_STMT);
			
			pstmt.setInt(1, articleId);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				commentsVO = new CommentsVO();
				commentsVO.setCommentId(rs.getInt("comment_id"));
				commentsVO.setArticleId(rs.getInt("article_id"));
				commentsVO.setMemberId(rs.getInt("member_id"));
				commentsVO.setCommentContent(rs.getString("comment_content"));
				commentsVO.setCommentStatus(rs.getInt("comment_status"));
				commentsVO.setReleaseTime(rs.getTimestamp("release_time"));
				list.add(commentsVO); // Store the row in the list
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

//	public static void main(String[] args) {
//
//		CommentsJDBCDAO dao = new  CommentsJDBCDAO();

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
//		List<CommentsVO> list = dao.getAll();
//		for (CommentsVO aArt : list) {
//			System.out.print(aArt.getCommentId() + ",");
//			System.out.print(aArt.getArticleId() + ",");
//			System.out.print(aArt.getMemberId() + ",");
//			System.out.print(aArt.getCommentContent() + ",");
//			System.out.print(aArt.getCommentStatus()+ ",");
//			System.out.print(aArt.getReleaseTime() );
//			System.out.println();
//		}
	}
